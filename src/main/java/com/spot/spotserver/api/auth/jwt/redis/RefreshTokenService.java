package com.spot.spotserver.api.auth.jwt.redis;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final TokenRepository tokenRepository;

    @Transactional
    public void saveRefreshToken(final Long userId, final String refreshToken) {
        // 기존 리프레시 토큰 삭제
        deleteRefreshToken(userId);

        // 새로운 리프레시 토큰 저장
        tokenRepository.save(Token.of(userId, refreshToken));
    }

    public Long findIdByRefreshToken(final String refreshToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 사용자의 리프레시 토큰이 존재하지 않습니다.")
                );
        return token.getId();
    }

    @Transactional
    public void deleteRefreshToken(final Long userId) {
        Optional<Token> token = tokenRepository.findById(userId);
        if (token.isPresent()) {
            tokenRepository.delete(token.get());
        } else {
            System.out.println("삭제할 리프레시 토큰이 존재하지 않습니다. 사용자 ID: " + userId);
        }
    }
}