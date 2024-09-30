package com.spot.spotserver.api.user.controller;

import com.spot.spotserver.api.quiz.dto.UserBadgeResponse;
import com.spot.spotserver.api.spot.dto.response.UserLikedSpotsResponse;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.api.user.dto.request.ColorRequest;
import com.spot.spotserver.api.user.dto.request.NicknameRequest;
import com.spot.spotserver.api.user.dto.request.ProfileImageRequest;
import com.spot.spotserver.api.user.dto.response.*;
import com.spot.spotserver.api.user.service.UserService;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/nickname")
    public ApiResponse<NicknameResponse> getNickname(@CurrentUser User user) {
        String nickname = userService.getNickname(user);
        NicknameResponse result = new NicknameResponse(nickname);
        return ApiResponse.success(SuccessCode.GET_NICKNAME_SUCCESS, result);
    }

    @PostMapping("/profile")
    public ApiResponse<ProfileImageResponse> registerProfile(@ModelAttribute ProfileImageRequest request,
                                                             @CurrentUser User user) throws IOException {
        String profileUrl = userService.saveProfile(request, user);
        ProfileImageResponse result = new ProfileImageResponse(profileUrl);
        return ApiResponse.success(SuccessCode.REGISTER_PROFILE_SUCCESS, result);
    }

    @PatchMapping("/profile")
    public ApiResponse<ProfileImageResponse> updateProfile(@ModelAttribute ProfileImageRequest request,
                                                           @CurrentUser User user) throws IOException {
        String profileUrl = userService.saveProfile(request, user);
        ProfileImageResponse result = new ProfileImageResponse(profileUrl);
        return ApiResponse.success(SuccessCode.UPDATE_PROFILE_SUCCESS, result);
    }

    @PostMapping("/color")
    public ApiResponse<ColorResponse> registerColor(@RequestBody ColorRequest request,
                                                    @CurrentUser User user) {
        String color = userService.saveColor(request, user);
        ColorResponse result = new ColorResponse(color);
        return ApiResponse.success(SuccessCode.REGISTER_COLOR_SUCCESS, result);
    }

    @PatchMapping("/color")
    public ApiResponse<ColorResponse> updateColor(@RequestBody ColorRequest request,
                                                  @CurrentUser User user) {
        String color = userService.saveColor(request, user);
        ColorResponse result = new ColorResponse(color);
        return ApiResponse.success(SuccessCode.UPDATE_COLOR_SUCCESS, result);
    }

    @GetMapping("/profile")
    public ApiResponse<ProfileResponse> getProfile(@CurrentUser User user) {
        ProfileResponse result = userService.getProfile(user);
        return ApiResponse.success(SuccessCode.GET_PROFILE_SUCCESS, result);
    }

    @GetMapping("/likes")
    public ApiResponse<List<UserLikedSpotsResponse>> getLikedSpots(@CurrentUser User user) {
        List<UserLikedSpotsResponse> result = userService.getLikedSpots(user);
        return ApiResponse.success(SuccessCode.GET_LIKED_SPOT_SUCCESS, result);
    }

    @GetMapping("/badge")
    public ApiResponse<List<UserBadgeResponse>> getBadges(@CurrentUser User user) {
        List<UserBadgeResponse> result = userService.getBadgeCountByRegion(user);
        return ApiResponse.success(SuccessCode.GET_BADGE_SUCCESS, result);
    }

    @GetMapping("/level")
    public ApiResponse<ProfileLevelResponse> getProfileLevel(@CurrentUser User user) {
        ProfileLevelResponse result = userService.getProfileLevel(user);
        return ApiResponse.success(SuccessCode.GET_LEVEL_SUCCESS, result);
    }
}
