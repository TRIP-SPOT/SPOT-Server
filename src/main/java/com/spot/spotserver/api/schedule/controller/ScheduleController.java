package com.spot.spotserver.api.schedule.controller;

import com.spot.spotserver.api.schedule.dto.ScheduleRequest;
import com.spot.spotserver.api.schedule.dto.ScheduleResponse;
import com.spot.spotserver.api.schedule.service.ScheduleService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/")
    public ApiResponse<ScheduleResponse> createSchedule(@RequestPart ScheduleRequest scheduleRequest,
                                                        @RequestPart MultipartFile image,
                                                        @CurrentUser User user) throws IOException {
        ScheduleResponse scheduleResponse = this.scheduleService.createSchedule(scheduleRequest, image, user);
        return ApiResponse.success(SuccessCode.CREATE_SCHEDULE_SUCCESS, scheduleResponse);
    }
}
