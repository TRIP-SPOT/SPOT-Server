package com.spot.spotserver.api.badge.controller;

import com.spot.spotserver.api.badge.domain.AcquisitionType;
import com.spot.spotserver.api.badge.dto.request.BadgeRequest;
import com.spot.spotserver.api.badge.service.BadgeService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/badge")
public class BadgeController {
    private final BadgeService badgeService;

    @PostMapping()
    public <T> ApiResponse<T> createBadgeByFilter(@RequestBody @Valid BadgeRequest badgeRequest,
                                           @CurrentUser User user) {
        this.badgeService.createBadge(AcquisitionType.BY_CAMERA_FILTER, badgeRequest.getRegion(), badgeRequest.getCity(), user);
        return ApiResponse.success(SuccessCode.CREATE_BADGE_SUCCESS);
    }
}
