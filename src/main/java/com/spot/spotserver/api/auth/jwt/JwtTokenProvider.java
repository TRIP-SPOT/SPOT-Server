package com.spot.spotserver.api.auth.jwt;

import com.spot.spotserver.api.auth.exception.EmptyJwtTokenException;
import com.spot.spotserver.api.auth.exception.ExpiredJwtTokenException;
import com.spot.spotserver.api.auth.exception.InvalidJwtTokenException;
import com.spot.spotserver.api.auth.exception.UnsupportedJwtTokenException;
import com.spot.spotserver.common.payload.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String USER_ID = "userId";
    private static final Long ACCESS_TOKEN_EXPIRATION_TIME =  60 * 60 * 1000L * 24; // 1일
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME =  60 * 60 * 1000L * 24 * 14; // 14일

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @PostConstruct
    protected void init() {
        // base64 라이브러리에서 encodeToString을 이용해서 byte[] 형식을 String 형식으로 변환
        JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey getSigningKey() {
        String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes()); // SecretKey 통해 서명 생성
        return Keys.hmacShaKeyFor(encodedKey.getBytes());   // HMAC (Hash-based Message Authentication Code) 알고리즘 사용
    }

    public JwtValidationType validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new EmptyJwtTokenException(ErrorCode.EMPTY_JWT);
        }

        try {
            final Claims claims = getBody(token);
            return JwtValidationType.VALID_JWT;
        } catch (MalformedJwtException ex) {
            throw new InvalidJwtTokenException(ErrorCode.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException ex) {
            throw new ExpiredJwtTokenException(ErrorCode.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtTokenException(ErrorCode.UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException ex) {
            throw new EmptyJwtTokenException(ErrorCode.EMPTY_JWT);
        }
    }

    private Claims getBody(final String token) {
        try {
            // JWT 디코딩 시 로그를 추가
            System.out.println("Token: " + token);

            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // 예외 발생 시 로그 출력
            System.err.println("Error decoding JWT: " + e.getMessage());
            e.printStackTrace();
            throw e; // 예외를 다시 던져 호출자에게 알리기
        }
    }

    public Long getUserFromJwt(String token) {
        Claims claims = getBody(token);
        return Long.valueOf(claims.get(USER_ID).toString());
    }

    public String issueAccessToken(final Authentication authentication) {
        return issueToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME);
    }
    public String issueRefreshToken(final Authentication authentication) {
        return issueToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String issueToken(
            final Authentication authentication,
            final Long expiredTime
    ) {
        final Date now = new Date();

        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredTime));  // 만료 시간 설정

        claims.put(USER_ID, authentication.getPrincipal());
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Header
                .setClaims(claims) // Claim
                .signWith(getSigningKey()) // Signature
                .compact();
    }
}
