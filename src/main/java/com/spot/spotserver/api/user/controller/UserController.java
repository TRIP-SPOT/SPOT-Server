package com.spot.spotserver.api.user.controller;

import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.api.user.dto.request.NicknameRequest;
import com.spot.spotserver.api.user.dto.response.NicknameResponse;
import com.spot.spotserver.api.user.service.UserService;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/nickname")
    public ApiResponse registerNickname(@RequestBody NicknameRequest request,
                                        @CurrentUser User user) {
        String nickname = userService.saveNickname(request, user);
        NicknameResponse result = new NicknameResponse(nickname);
        return ApiResponse.success(SuccessCode.REGISTER_NICKNAME_SUCCESS, result);
    }

    @PatchMapping("/nickname")
    public ApiResponse updateNickname(@RequestBody NicknameRequest request,
                                      @CurrentUser User user) {
        String nickname = userService.saveNickname(request, user);
        NicknameResponse result = new NicknameResponse(nickname);
        return ApiResponse.success(SuccessCode.UPDATE_NICKNAME_SUCCESS, result);
    }
}
