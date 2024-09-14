package com.spot.spotserver.api.record.controller;

import com.spot.spotserver.api.record.domain.Region;
import com.spot.spotserver.api.record.dto.RecordRequest;
import com.spot.spotserver.api.record.dto.RecordResponse;
import com.spot.spotserver.api.record.dto.RecordUpdateRequest;
import com.spot.spotserver.api.record.dto.RegionalRecordResponse;
import com.spot.spotserver.api.record.service.RecordService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/region/{regionNumber}")
    public ApiResponse<List<RegionalRecordResponse>> getRegionalRecords(@PathVariable int regionNumber) {
        List<RegionalRecordResponse> regionalRecordResponse = this.recordService.getRegionalRecord(Region.values()[regionNumber]);
        return ApiResponse.success(SuccessCode.GET_REGIONAL_RECORDS_SUCCESS, regionalRecordResponse);
    }

    @GetMapping("/{id}")
    public ApiResponse<RecordResponse> getRecord(@PathVariable Long id) {
        RecordResponse recordResponse = this.recordService.getRecord(id);
        return ApiResponse.success(SuccessCode.GET_RECORD_SUCCESS, recordResponse);
    }

    @PatchMapping("/{id}")
    public <T> ApiResponse<T> updateRecord(@PathVariable Long id,
                                    @RequestPart("recordUpdate") RecordUpdateRequest recordUpdateRequest,
                                    @RequestPart("addImages") Optional<List<MultipartFile>> addImages,
                                    @CurrentUser User user) {
        this.recordService.updateRecord(id, recordUpdateRequest, addImages, user);
        return ApiResponse.success(SuccessCode.UPDATE_RECORD_SUCCESS);
    }

    @DeleteMapping("/{id}")
    public <T> ApiResponse<T> deleteRecord(@PathVariable Long id) {
        this.recordService.deleteRecord(id);
        return ApiResponse.success(SuccessCode.DELETE_RECORD_SUCCESS);
    }
}
