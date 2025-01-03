package com.spot.spotserver.api.auth.controller;

import com.spot.spotserver.api.auth.dto.request.TokenRequest;
import com.spot.spotserver.api.auth.dto.response.TokenResponse;
import com.spot.spotserver.api.auth.service.AuthService;
import com.spot.spotserver.common.payload.ApiResponse;
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
}
