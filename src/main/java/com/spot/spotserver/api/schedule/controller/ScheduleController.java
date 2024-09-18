package com.spot.spotserver.api.schedule.controller;

import com.spot.spotserver.api.schedule.dto.ScheduleRequest;
import com.spot.spotserver.api.schedule.dto.ScheduleResponse;
import com.spot.spotserver.api.schedule.service.ScheduleService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @DeleteMapping("/{id}")
    public <T> ApiResponse<T> deleteSchedule(@PathVariable Long id) {
        this.scheduleService.deleteSchedule(id);
        return ApiResponse.success(SuccessCode.DELETE_SCHEDULE_SUCCESS);
    }
}
