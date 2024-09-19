package com.spot.spotserver.api.record.controller;

import com.spot.spotserver.common.domain.Region;
import com.spot.spotserver.api.record.dto.*;
import com.spot.spotserver.api.record.service.RecordService;
import com.spot.spotserver.api.record.service.RepresentativeImageService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {
    private final RecordService recordService;
    private final RepresentativeImageService representativeImageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<RecordResponse> createRecord(@ModelAttribute RecordRequest recordRequest,
                                          @CurrentUser User user) {
        RecordResponse recordResponse = this.recordService.createRecord(recordRequest, user);
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

    @PostMapping("/representative")
    public ApiResponse<RepresentativeImageResponse> createRepresentativeImage(@ModelAttribute RepresentativeImageRequest representativeImageRequest,
                                                                              @CurrentUser User user) throws IOException {
        RepresentativeImageResponse representativeImageResponse = this.representativeImageService.createRepresentativeImage(representativeImageRequest, user);
        return ApiResponse.success(SuccessCode.CREATE_REPRESENTATIVE_IMAGE_SUCCESS, representativeImageResponse);
    }

    @GetMapping("/representative")
    public ApiResponse<List<RepresentativeImageResponse>> getRepresentativeImages() {
        List<RepresentativeImageResponse> representativeImageResponses = this.representativeImageService.getRepresentativeImages();
        return ApiResponse.success(SuccessCode.GET_REPRESENTATIVE_IMAGE_SUCCESS, representativeImageResponses);
    }

    @PatchMapping("/representative/{id}")
    public <T> ApiResponse<T> updateRepresentativeImage(@PathVariable Long id,
                                                        @RequestPart MultipartFile newImage,
                                                        @CurrentUser User user) throws IOException {
        this.representativeImageService.updateRepresentativeImage(id, newImage, user);
        return ApiResponse.success(SuccessCode.UPDATE_REPRESENTATIVE_IMAGE_SUCCESS);
    }
}
