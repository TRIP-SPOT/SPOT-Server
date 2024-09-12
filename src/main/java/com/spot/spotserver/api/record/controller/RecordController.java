package com.spot.spotserver.api.record.controller;

import com.spot.spotserver.api.record.dto.RecordRequest;
import com.spot.spotserver.api.record.dto.RecordResponse;
import com.spot.spotserver.api.record.service.RecordService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {
    private final RecordService recordService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<RecordResponse> createRecord(@RequestPart("record") RecordRequest recordRequest,
                                          @RequestPart("images") List<MultipartFile> images,
                                          @CurrentUser User user) {
        RecordResponse recordResponse = this.recordService.createRecord(recordRequest, images, user);
        return ApiResponse.success(SuccessCode.CREATE_RECORD_SUCCESS, recordResponse);
    }
}
