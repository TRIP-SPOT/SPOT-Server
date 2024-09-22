package com.spot.spotserver.api.spot.controller;

import com.spot.spotserver.api.spot.dto.response.AccessibleSpotResponse;
import com.spot.spotserver.api.spot.dto.response.SpotAroundResponse;
import com.spot.spotserver.api.spot.dto.response.SpotDetailsResponse;
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
    public ApiResponse<SpotDetailsResponse> getSpotDetails(@PathVariable Integer contentId, @CurrentUser User user) {
        SpotDetailsResponse result = spotService.getSpotDetails(contentId, user);
        return ApiResponse.success(SuccessCode.GET_SPOT_DETAIL_SUCCESS, result);
    }

    @GetMapping("/spot")
    public ApiResponse<List<AccessibleSpotResponse>> getAccessibleSpot(@RequestParam double latitude,
                                                                       @RequestParam double longitude) {
        List<AccessibleSpotResponse> accessibleSpotResponses = this.spotService.getAccessibleSpot(latitude, longitude);
        return ApiResponse.success(SuccessCode.GET_WITHIN_RADIUS_SPOT_LIST_SUCCESS, accessibleSpotResponses);
    }

    @GetMapping("/spot/{contentId}/arounds")
    public ApiResponse<SpotAroundResponse> getSpotAroundList(@PathVariable Integer contentId) {
        SpotAroundResponse result = spotService.getSpotAroundList(contentId);
        return ApiResponse.success(SuccessCode.GET_SPOT_AROUND_SUCCESS, result);
    }

    @GetMapping("/around/{contentId}")
    public ApiResponse<SpotDetailsResponse> getAroundDetails(@PathVariable Integer contentId, @CurrentUser User user) {
        SpotDetailsResponse result = spotService.getSpotDetails(contentId, user);
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
}
