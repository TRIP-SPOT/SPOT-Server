package com.spot.spotserver.api.spot.controller;

import com.spot.spotserver.api.spot.dto.response.SpotDetailsResponse;
import com.spot.spotserver.api.spot.service.SpotService;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
