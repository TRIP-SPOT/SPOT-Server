package com.spot.spotserver.api.auth.service;

import com.spot.spotserver.api.auth.dto.response.KakaoUserResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoService {

    private final WebClient kakaoApiWebClient;  // 사용자 정보 조회
    private final WebClient kakaoAuthWebClient; // 인증 관련 (로그아웃 등)

    public KakaoService(WebClient.Builder webClientBuilder) {
        // Kakao API WebClient
        this.kakaoApiWebClient = webClientBuilder
                .baseUrl("https://kapi.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        // Kakao Auth WebClient
        this.kakaoAuthWebClient = webClientBuilder
                .baseUrl("https://kauth.kakao.com/oauth")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public KakaoUserResponse getUserInformation(String accessToken) {
        return kakaoApiWebClient.get()
                .uri("/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserResponse.class)
                .block();
    }

    public void logout(String clientId, String logoutRedirectUri) {
        kakaoAuthWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/logout")
                        .queryParam("client_id", clientId)
                        .queryParam("logout_redirect_uri", logoutRedirectUri)
                        .build())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}