package com.spot.spotserver.api.schedule.controller;

import com.spot.spotserver.api.schedule.dto.*;
import com.spot.spotserver.api.schedule.service.ScheduleService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("")
    public ApiResponse<ScheduleResponse> createSchedule(@RequestPart(name = "schedule") ScheduleRequest scheduleRequest,
                                                        @RequestPart MultipartFile image,
                                                        @CurrentUser User user) throws IOException {
        ScheduleResponse scheduleResponse = this.scheduleService.createSchedule(scheduleRequest, image, user);
        return ApiResponse.success(SuccessCode.CREATE_SCHEDULE_SUCCESS, scheduleResponse);
    }

    @GetMapping("")
    public ApiResponse<List<ScheduleResponse>> getSchedules(@CurrentUser User user) {
        List<ScheduleResponse> schedules = this.scheduleService.getSchedules(user);
        return ApiResponse.success(SuccessCode.GET_SCHEDULE_LIST_SUCCESS, schedules);
    }

    @DeleteMapping("/{id}")
    public <T> ApiResponse<T> deleteSchedule(@PathVariable Long id) {
        this.scheduleService.deleteSchedule(id);
        return ApiResponse.success(SuccessCode.DELETE_SCHEDULE_SUCCESS);
    }

    @PatchMapping("/date/{id}")
    public ApiResponse<ScheduleDurationUpdateResponse> updateDuration(@PathVariable Long id,
                                                                      @RequestBody @Valid ScheduleDurationUpdateRequest scheduleDurationUpdateRequest) {
        ScheduleDurationUpdateResponse scheduleDurationUpdateResponse = this.scheduleService.updateDuration(id, scheduleDurationUpdateRequest);
        return ApiResponse.success(SuccessCode.UPDATE_SCHEDULE_DURATION_SUCCESS, scheduleDurationUpdateResponse);
    }

    @PatchMapping("/image/{id}")
    public ApiResponse<ScheduleImageUpdateResponse> updateImage(@PathVariable Long id,
                                                                @RequestParam MultipartFile imageFile,
                                                                @CurrentUser User user) throws IOException {
        ScheduleImageUpdateResponse scheduleImageUpdateResponse = this.scheduleService.updateImage(id, imageFile, user);
        return ApiResponse.success(SuccessCode.UPDATE_SCHEDULE_IMAGE_SUCCESS, scheduleImageUpdateResponse);
    }

    @PostMapping("/location")
    public ApiResponse<LocationResponse> createLocation(@RequestBody @Valid LocationRequest locationRequest) {
        LocationResponse locationResponse = this.scheduleService.createLocation(locationRequest);
        return ApiResponse.success(SuccessCode.ADD_LOCATION_SUCCESS, locationResponse);
    }

    @DeleteMapping("/{ids}")
    public <T> ApiResponse<T> deleteLocations(@PathVariable List<Long> ids) {
        this.scheduleService.deleteLocations(ids);
        return ApiResponse.success(SuccessCode.DELETE_LOCATIONS_SUCCESS);
    }
}
