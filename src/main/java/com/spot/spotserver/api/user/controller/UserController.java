package com.spot.spotserver.api.user.controller;

import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.api.user.dto.request.NicknameRequest;
import com.spot.spotserver.api.user.dto.request.ProfileRequest;
import com.spot.spotserver.api.user.dto.response.NicknameResponse;
import com.spot.spotserver.api.user.dto.response.ProfileResponse;
import com.spot.spotserver.api.user.service.UserService;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @PostMapping("/profile")
    public ApiResponse<ProfileResponse> registerProfile(@ModelAttribute ProfileRequest request,
                                                        @CurrentUser User user) throws IOException {
        String profileUrl = userService.saveProfile(request, user);
        ProfileResponse result = new ProfileResponse(profileUrl);
        return ApiResponse.success(SuccessCode.REGISTER_PROFILE_SUCCESS, result);
    }

    @PatchMapping("/profile")
    public ApiResponse<ProfileResponse> updateProfile(@ModelAttribute ProfileRequest request,
                                                      @CurrentUser User user) throws IOException {
        String profileUrl = userService.saveProfile(request, user);
        ProfileResponse result = new ProfileResponse(profileUrl);
        return ApiResponse.success(SuccessCode.UPDATE_PROFILE_SUCCESS, result);
    }
}
