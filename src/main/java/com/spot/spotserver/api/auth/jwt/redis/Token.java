package com.spot.spotserver.api.auth.jwt.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "Token", timeToLive = 60 * 60 * 1000L * 24 * 14)
@AllArgsConstructor
@Getter
@Builder
public class Token {

    @Id
    private Long id;

    private String refreshToken;

    public static Token of(
            final Long id,
            final String refreshToken
    ) {
        String cleanedRefreshToken = refreshToken.replace("\"", "");

        return Token.builder()
                .id(id)
                .refreshToken(cleanedRefreshToken)
                .build();
    }
}