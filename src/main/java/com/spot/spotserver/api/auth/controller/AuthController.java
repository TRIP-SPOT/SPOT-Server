package com.spot.spotserver.api.auth.controller;

import com.spot.spotserver.api.auth.dto.request.TokenRequest;
import com.spot.spotserver.api.auth.dto.response.TokenResponse;
import com.spot.spotserver.api.auth.exception.InvalidJwtTokenException;
import com.spot.spotserver.api.auth.service.AuthService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.ErrorCode;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/login/kakao")
    public ApiResponse<TokenResponse> login(@RequestParam final String accessToken) {

        TokenResponse successResponse = authService.login(accessToken);

        TokenResponse result = new TokenResponse(
                successResponse.accessToken(),
                successResponse.refreshToken()
        );
        return ApiResponse.success(SuccessCode.LOGIN_SUCCESS, result);
    }

    @PostMapping("/api/refresh")
    public ApiResponse<TokenResponse> reissueToken(@RequestBody TokenRequest request) {

        TokenResponse result = authService.reissueToken(request.refreshToken());
        return ApiResponse.success(SuccessCode.REISSUE_TOKEN_SUCCESS, result);
    }

    @PostMapping("/api/logout")
    public ApiResponse logout(@RequestHeader("Authorization") String authorizationHeader, @CurrentUser User user) {

        String accessToken = extractAccessToken(authorizationHeader);
        authService.kakaoLogout(accessToken, user);
        return ApiResponse.success(SuccessCode.LOGOUT_SUCCESS);
    }

    private String extractAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new InvalidJwtTokenException(ErrorCode.INVALID_JWT_TOKEN);
    }
}