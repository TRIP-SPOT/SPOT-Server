package com.spot.spotserver.api.spot.controller;

import com.spot.spotserver.api.spot.dto.response.*;
import com.spot.spotserver.api.spot.service.SpotService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SpotController {

    private final SpotService spotService;

    @GetMapping("/spot/{contentId}")
    public ApiResponse<SpotDetailsResponse> getSpotDetails(@PathVariable Integer contentId,
                                                           @RequestParam Long workId,
                                                           @CurrentUser User user) {
        SpotDetailsResponse result = spotService.getSpotDetails(contentId, workId, user);
        return ApiResponse.success(SuccessCode.GET_SPOT_DETAIL_SUCCESS, result);
    }

    @GetMapping("/spot")
    public ApiResponse<List<AccessibleSpotWithQuizResponse>> getAccessibleSpot(@CurrentUser User user,
                                                                               @RequestParam double latitude,
                                                                               @RequestParam double longitude) {
        List<AccessibleSpotWithQuizResponse> accessibleSpotWithQuizRespons = this.spotService.getAccessibleSpotWithQuiz(user, latitude, longitude);
        return ApiResponse.success(SuccessCode.GET_ACCESSIBLE_QUIZ_SUCCESS, accessibleSpotWithQuizRespons);
    }

    @GetMapping("/spot/{contentId}/arounds")
    public ApiResponse<SpotAroundResponse> getSpotAroundList(@PathVariable Integer contentId,
                                                             @RequestParam Long workId) {
        SpotAroundResponse result = spotService.getSpotAroundList(contentId, workId);
        return ApiResponse.success(SuccessCode.GET_SPOT_AROUND_SUCCESS, result);
    }

    @GetMapping("/around/{contentId}")
    public ApiResponse<AroundDetailsResponse> getAroundDetails(@PathVariable Integer contentId) {
        AroundDetailsResponse result = spotService.getAroundDetails(contentId);
        return ApiResponse.success(SuccessCode.GET_AROUND_DETAIL_SUCCESS, result);
    }

    @PostMapping("/spot/{id}/likes")
    public ApiResponse likeSpot(@PathVariable("id") Long spotId,
                                @CurrentUser User user) {
        spotService.likeSpot(spotId, user);
        return ApiResponse.success(SuccessCode.LIKE_SPOT_SUCCESS);
    }

    @DeleteMapping("/spot/{id}/likes")
    public ApiResponse unlikeSpot(@PathVariable("id") Long spotId,
                                  @CurrentUser User user) {
        spotService.unlikeSpot(spotId, user);
        return ApiResponse.success(SuccessCode.UNLIKE_SPOT_SUCCESS);
    }

    @GetMapping("/spot/home")
    public ApiResponse<List<SpotSummaryResponse>> getTop5Spots(@CurrentUser User user) {
        List<SpotSummaryResponse> result = spotService.getTop5Spots(user);
        return ApiResponse.success(SuccessCode.GET_TOP_LIKE_SPOT_SUCCESS, result);
    }

    @GetMapping("/spot/nearby")
    public ApiResponse<List<SpotSummaryResponse>> getAccessible5Spots(@CurrentUser User user,
                                                                      @RequestParam double latitude,
                                                                      @RequestParam double longitude) {
        List<SpotSummaryResponse> result = spotService.getAccessible5Spots(user, latitude, longitude);
        return ApiResponse.success(SuccessCode.GET_WITHIN_RADIUS_SPOT_LIST_SUCCESS, result);
    }

    @GetMapping("/spot/search")
    public ApiResponse<List<SpotSearchResponse>> searchSpotsByWorkName(@RequestParam String keyword, @CurrentUser User user) {
        List<SpotSearchResponse> result = spotService.searchSpotsByWorkName(keyword, user);
        return ApiResponse.success(SuccessCode.GET_SEARCH_SPOT_SUCCESS, result);
    }
}

