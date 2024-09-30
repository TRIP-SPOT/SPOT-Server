package com.spot.spotserver.api.auth.jwt.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "Token", timeToLive = 60 * 60 * 24 * 14)
@AllArgsConstructor
@Getter
@Builder
public class Token {

    @Id
    private String id;

    private String refreshToken;

    public static Token of(
            final Long id,
            final String refreshToken
    ) {
        return Token.builder()
                .id(id.toString())
                .refreshToken(refreshToken)
                .build();
    }
}