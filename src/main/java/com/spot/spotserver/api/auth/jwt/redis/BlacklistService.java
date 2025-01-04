package com.spot.spotserver.api.auth.jwt.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BlacklistService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String BLACKLIST_KEY_PREFIX = "blacklist:";
    private static final Long ACCESS_TOKEN_EXPIRATION_TIME =  60 * 60 * 1000L * 5;
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME =  60 * 60 * 1000L * 24 * 14;

    // 토큰을 블랙리스트에 추가
    public void addToBlacklist(String token, boolean isRefreshToken) {
        long expirationTime = isRefreshToken ? REFRESH_TOKEN_EXPIRATION_TIME : ACCESS_TOKEN_EXPIRATION_TIME;
        redisTemplate.opsForValue().set(BLACKLIST_KEY_PREFIX + token, "blacklisted", expirationTime, TimeUnit.MILLISECONDS);
    }

    // 토큰이 블랙리스트에 있는지 확인
    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(BLACKLIST_KEY_PREFIX + token);
    }
}
