package com.spot.spotserver.api.auth.jwt;

import com.spot.spotserver.api.auth.exception.JwtCustomException;
import com.spot.spotserver.api.auth.handler.UserAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.spot.spotserver.api.auth.jwt.JwtValidationType.VALID_JWT;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if ("/api/login/kakao".equals(requestURI) || "/api/refresh".equals(requestURI)) {
            // 로그인 요청의 경우 JWT 검증 건너뜀
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = getJwtFromRequest(request);
            if (jwtTokenProvider.validateToken(token) == VALID_JWT) {
                Long memberId = jwtTokenProvider.getUserFromJwt(token);
                // authentication 객체 생성 -> principal에 유저정보를 담는다
                UserAuthentication userAuthentication = new UserAuthentication(memberId.toString(), null, null);
                userAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userAuthentication);
            }
        } catch (JwtCustomException e) {
            // JWT 관련 예외 처리
            response.sendError(e.getError().getHTTPStatusCode(), e.getError().getMessage());
            return;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
            return;
        }
        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}
