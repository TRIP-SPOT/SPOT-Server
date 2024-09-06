package com.spot.spotserver.api.user.controller;

import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.api.user.dto.request.NicknameRequest;
import com.spot.spotserver.api.user.dto.response.NicknameResponse;
import com.spot.spotserver.api.user.service.UserService;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/nickname")
    public ApiResponse registerNickname(@RequestBody NicknameRequest request,
                                        @CurrentUser User user) {
        String nickname = userService.registerNickname(request, user);
        NicknameResponse result = new NicknameResponse(nickname);
        return ApiResponse.success(SuccessCode.LOGIN_SUCCESS, result);
    }
}
