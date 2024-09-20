package com.spot.spotserver.api.spot.controller;

import com.spot.spotserver.api.spot.dto.response.AccessibleSpotResponse;
import com.spot.spotserver.api.spot.dto.response.SpotAroundResponse;
import com.spot.spotserver.api.spot.dto.response.SpotDetailsResponse;
import com.spot.spotserver.api.spot.service.SpotService;
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
    public ApiResponse<SpotDetailsResponse> getSpotDetails(@PathVariable Integer contentId) {
        SpotDetailsResponse result = spotService.getSpotDetails(contentId);
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
    public ApiResponse<SpotDetailsResponse> getAroundDetails(@PathVariable Integer contentId) {
        SpotDetailsResponse result = spotService.getSpotDetails(contentId);
        return ApiResponse.success(SuccessCode.GET_AROUND_DETAIL_SUCCESS, result);
    }
}
