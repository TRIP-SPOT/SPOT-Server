package com.spot.spotserver.api.auth.jwt.redis;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final TokenRepository tokenRepository;

    @Transactional
    public void saveRefreshToken(final Long userId, final String refreshToken) {
        String strUserId = userId.toString();
        if (tokenRepository.existsById(strUserId)) {
            tokenRepository.deleteById(strUserId);
        }
        tokenRepository.save(Token.of(userId, refreshToken));
    }

    public String getRefreshToken(final Long userId) {
        return tokenRepository.findById(userId.toString())
                .map(Token::getRefreshToken)
                .orElse(null);
    }

    public void deleteRefreshToken(final Long userId) {
        String strUserId = userId.toString();
        if (tokenRepository.existsById(strUserId)) {
            tokenRepository.deleteById(strUserId);
        } else {
            System.out.println("삭제할 리프레시 토큰이 존재하지 않습니다. 사용자 ID: " + userId);
        }
    }
}
