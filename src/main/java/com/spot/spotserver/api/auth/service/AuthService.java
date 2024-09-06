package com.spot.spotserver.api.auth.service;

import com.spot.spotserver.api.auth.client.KakaoApiClient;
import com.spot.spotserver.api.auth.client.KakaoAuthApiClient;
import com.spot.spotserver.api.auth.dto.response.KakaoAccessTokenResponse;
import com.spot.spotserver.api.auth.dto.response.KakaoUserResponse;
import com.spot.spotserver.api.auth.dto.response.TokenResponse;
import com.spot.spotserver.api.auth.handler.UserAuthentication;
import com.spot.spotserver.api.auth.jwt.JwtTokenProvider;
import com.spot.spotserver.api.auth.jwt.redis.RefreshTokenService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.api.user.repository.UserRepository;
import com.spot.spotserver.api.user.service.UserService;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String AUTH_CODE = "authorization_code";
    private static final String REDIRECT_URI = "http://localhost:8080/api/login/kakao";

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    private final KakaoApiClient kakaoApiClient;
    private final KakaoAuthApiClient kakaoAuthApiClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public TokenResponse login(final String authorizationCode) {
        if (authorizationCode == null || authorizationCode.isEmpty()) {
            throw new IllegalArgumentException("인가코드가 적절하지 않습니다.");
        }

        // 카카오에서 액세스 토큰과 리프레시 토큰 받아오기
        KakaoAccessTokenResponse kakaoTokenResponse;
        try {
            kakaoTokenResponse = getOAuth2Authentication(authorizationCode);
        } catch (FeignException e) {
            throw new IllegalArgumentException("엑세스 토큰 조회에 실패하였습니다. 사용한 인가코드: " + authorizationCode, e);
        }

        // 카카오 액세스 토큰으로 사용자 정보 가져오기
        KakaoUserResponse userResponse;
        try {
            userResponse = getUserInfo(kakaoTokenResponse.accessToken());
        } catch (FeignException e) {
            throw new IllegalArgumentException("사용자 정보 조회에 실패하였습니다. 사용한 액세스 토큰: " + kakaoTokenResponse.accessToken(), e);
        }

        // 서비스 자체 JWT 액세스 및 리프레시 토큰 생성
        UserAuthentication authentication = new UserAuthentication(userResponse.id(), null, null);
        String jwtAccessToken = jwtTokenProvider.issueAccessToken(authentication);
        String jwtRefreshToken = jwtTokenProvider.issueRefreshToken(authentication);

        // 리프레시 토큰 레디스에 저장
        refreshTokenService.saveRefreshToken(userResponse.id(), jwtRefreshToken);

        // JWT 액세스 토큰 및 리프레시 토큰을 클라이언트에 반환
        return new TokenResponse(jwtAccessToken, jwtRefreshToken);
    }

    private KakaoAccessTokenResponse getOAuth2Authentication(final String authorizationCode) {
        KakaoAccessTokenResponse tokenResponse = kakaoAuthApiClient.getOAuth2AccessToken(
                AUTH_CODE, clientId, REDIRECT_URI, authorizationCode, clientSecret);
        return tokenResponse;
    }

    private KakaoUserResponse getUserInfo(final String accessToken) {
        return kakaoApiClient.getUserInformation("Bearer " + accessToken);
    }

    private TokenResponse processUser(KakaoUserResponse userResponse) {
        if (userService.isExistingUser(userResponse.id())) {
            return userService.getTokenByUserId(userService.getIdBySocialId(userResponse.id()));
        } else {
            return userService.getTokenByUserId(userService.createUser(userResponse));
        }
    }

    public User getUserFromAccessToken(String accessToken) {
        Long userId = jwtTokenProvider.getUserFromJwt(accessToken);
        return userRepository.findBySocialId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 존재하지 않습니다."));
    }
}
