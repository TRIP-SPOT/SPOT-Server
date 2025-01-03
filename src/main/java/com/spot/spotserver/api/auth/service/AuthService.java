package com.spot.spotserver.api.auth.service;

import com.spot.spotserver.api.auth.client.KakaoApiClient;
import com.spot.spotserver.api.auth.dto.response.KakaoUserResponse;
import com.spot.spotserver.api.auth.dto.response.TokenResponse;
import com.spot.spotserver.api.auth.exception.JwtCustomException;
import com.spot.spotserver.api.auth.exception.OAuth2TokenException;
import com.spot.spotserver.api.auth.handler.UserAuthentication;
import com.spot.spotserver.api.auth.jwt.JwtTokenProvider;
import com.spot.spotserver.api.auth.jwt.JwtValidationType;
import com.spot.spotserver.api.auth.jwt.redis.RefreshTokenService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.api.user.exception.UserNotFoundException;
import com.spot.spotserver.api.user.repository.UserRepository;
import com.spot.spotserver.api.user.service.UserService;
import com.spot.spotserver.common.payload.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    private final KakaoApiClient kakaoApiClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public TokenResponse login(final String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new OAuth2TokenException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        // 카카오 액세스 토큰으로 사용자 정보 가져오기
        KakaoUserResponse userResponse = getUserInfo(accessToken);
        Long userId = userService.processUser(userResponse);

        // 기존 리프레시 토큰 확인 및 재사용
        String existingRefreshToken = refreshTokenService.getRefreshToken(userId);
        String jwtRefreshToken = existingRefreshToken != null
                ? existingRefreshToken
                : jwtTokenProvider.issueRefreshToken(new UserAuthentication(userId, null, null));

        // Redis에 리프레시 토큰 저장
        refreshTokenService.saveRefreshToken(userService.getIdBySocialId(userResponse.id()), jwtRefreshToken);

        // 액세스 토큰 생성
        String jwtAccessToken = jwtTokenProvider.issueAccessToken(new UserAuthentication(userId, null, null));

        return TokenResponse.of(jwtAccessToken, jwtRefreshToken);
    }

    private KakaoUserResponse getUserInfo(final String accessToken) {
        return kakaoApiClient.getUserInformation("Bearer " + accessToken);
    }

    public User getUserFromAccessToken(String accessToken) {
        Long userId = jwtTokenProvider.getUserFromJwt(accessToken);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public TokenResponse reissueToken(final String refreshToken) {
        JwtValidationType validationType = jwtTokenProvider.validateToken(refreshToken);

        if (validationType != JwtValidationType.VALID_JWT) {
            throw new JwtCustomException(ErrorCode.INVALID_JWT_TOKEN);
        }

        Long userId = jwtTokenProvider.getUserFromJwt(refreshToken);
        UserAuthentication userAuthentication = new UserAuthentication(userId, null, null);
        String newAccessToken = jwtTokenProvider.issueAccessToken(userAuthentication);
        String newRefreshToken = jwtTokenProvider.issueRefreshToken(userAuthentication);

        // 새로운 리프레시 토큰으로 교체
        refreshTokenService.saveRefreshToken(userId, newRefreshToken);
        return TokenResponse.of(newAccessToken, newRefreshToken);
    }
}
