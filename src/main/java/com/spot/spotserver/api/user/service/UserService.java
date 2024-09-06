package com.spot.spotserver.api.user.service;

import com.spot.spotserver.api.auth.client.KakaoAccount;
import com.spot.spotserver.api.auth.dto.response.KakaoUserResponse;
import com.spot.spotserver.api.auth.dto.response.TokenResponse;
import com.spot.spotserver.api.auth.handler.UserAuthentication;
import com.spot.spotserver.api.auth.jwt.JwtTokenProvider;
import com.spot.spotserver.api.auth.jwt.redis.RefreshTokenService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.api.user.dto.request.NicknameRequest;
import com.spot.spotserver.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public Long createUser(final KakaoUserResponse userResponse) {
        String email = Optional.ofNullable(userResponse.kakaoAccount())
                .map(KakaoAccount::email)
                .orElse(null);
        User user = User.of(email, userResponse.id());
        return userRepository.save(user).getId();
    }

    public Long getIdBySocialId(final Long socialId) {
        User user = userRepository.findBySocialId(socialId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 사용자가 존재하지 않습니다.")
        );
        return user.getId();
    }

    public boolean isExistingUser(final Long socialId) {
        return userRepository.findBySocialId(socialId).isPresent();
    }

    public TokenResponse getTokenByUserId(final Long id) {
        UserAuthentication userAuthentication = new UserAuthentication(id, null, null);
        String refreshToken = jwtTokenProvider.issueRefreshToken(userAuthentication);
        refreshTokenService.saveRefreshToken(id, refreshToken);
        return TokenResponse.of(
                jwtTokenProvider.issueAccessToken(userAuthentication),
                refreshToken
        );
    }

    public TokenResponse reissueToken(final String refreshToken) {
        Long userId = jwtTokenProvider.getUserFromJwt(refreshToken);
        UserAuthentication userAuthentication = new UserAuthentication(userId, null, null);
        String newAccessToken = jwtTokenProvider.issueAccessToken(userAuthentication);
        String newRefreshToken = jwtTokenProvider.issueRefreshToken(userAuthentication);

        // 새로운 리프레시 토큰으로 교체
        refreshTokenService.saveRefreshToken(userId, newRefreshToken);
        return TokenResponse.of(newAccessToken, newRefreshToken);
    }

    public String registerNickname(NicknameRequest nicknameRequest, User user) {
        userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 존재하지 않습니다."));
        user.setNickname(nicknameRequest.nickname());
        userRepository.save(user);
        return nicknameRequest.nickname();
    }
}
