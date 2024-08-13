package com.spot.spotserver.api.auth.controller;

import com.spot.spotserver.api.auth.dto.response.TokenResponse;
import com.spot.spotserver.api.auth.service.AuthService;
import com.spot.spotserver.api.user.service.UserService;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/login/kakao")
    public ApiResponse<TokenResponse> login(@RequestParam final String authorizationCode) {

        TokenResponse successResponse = authService.login(authorizationCode);

        TokenResponse result = new TokenResponse(
                successResponse.accessToken(),
                successResponse.refreshToken()
        );
        return ApiResponse.success(SuccessCode.LOGIN_SUCCESS, result);
    }
}
