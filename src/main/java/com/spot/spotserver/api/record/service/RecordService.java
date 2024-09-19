package com.spot.spotserver.api.record.service;

import com.spot.spotserver.api.record.domain.Record;
import com.spot.spotserver.api.record.exception.RecordImageProcessingException;
import com.spot.spotserver.api.record.exception.RecordNotFoundException;
import com.spot.spotserver.common.domain.Region;
import com.spot.spotserver.api.record.dto.RecordRequest;
import com.spot.spotserver.api.record.dto.RecordResponse;
import com.spot.spotserver.api.record.dto.RecordUpdateRequest;
import com.spot.spotserver.api.record.dto.RegionalRecordResponse;
import com.spot.spotserver.api.record.repository.RecordRepository;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.payload.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {
    private final RecordRepository recordRepository;
    private final RecordImageService recordImageService;

    @Transactional
    public RecordResponse createRecord(RecordRequest recordRequest, User user) {
        Record newRecord = Record.builder()
                .title(recordRequest.getTitle())
                .description(recordRequest.getDescription())
                .region(recordRequest.getRegion())
                .city(recordRequest.getCity())
                .startDate(recordRequest.getStartDate())
                .endDate(recordRequest.getEndDate())
                .user(user)
                .build();

        this.recordRepository.save(newRecord);

        List<String> imageUrls = recordRequest.getImages()
                .stream()
                .map((image) -> {
                    try {
                        return recordImageService.createRecordImage(image, newRecord, user);
                    } catch (IOException e) {
                        throw new RecordImageProcessingException(ErrorCode.RECORD_IMAGE_PROCESSING_FAILED);;
                    }
                }).toList();

        return new RecordResponse (newRecord, imageUrls);
    }

    public List<RegionalRecordResponse> getRegionalRecord(Region region) {
        List<Record> records = this.recordRepository.findAllByRegion(region);
        return records.stream()
                .map((record) -> new RegionalRecordResponse(record, this.recordImageService.getRegionalThumbnailImage(record)))
                .toList();
    }

    public RecordResponse getRecord(Long id) {
        Record record = this.recordRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException(ErrorCode.RECORD_NOT_FOUND));
        List<String> images = this.recordImageService.getRecordImages(record);
        return new RecordResponse(record, images);
    }

    @Transactional
    public void updateRecord(Long id, RecordUpdateRequest recordUpdateRequest, Optional<List<MultipartFile>> addImages, User user) {
        Record updateRecord = this.recordRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException(ErrorCode.RECORD_NOT_FOUND));
        updateRecord.updateTitle(recordUpdateRequest.getTitle());
        updateRecord.updateDescription(recordUpdateRequest.getDescription());

        recordUpdateRequest.getDeleteImages().forEach(this.recordImageService::deleteRecordImageById);

        addImages.ifPresent(images -> {
            images.forEach(image -> {
                try {
                    recordImageService.createRecordImage(image, updateRecord, user);
                } catch (IOException e) {
                    throw new RecordImageProcessingException(ErrorCode.RECORD_IMAGE_PROCESSING_FAILED);
                }
            });
        });
    }

    @Transactional
    public void deleteRecord(Long id) {
        Record deleteRecord = this.recordRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException(ErrorCode.RECORD_NOT_FOUND));
        this.recordRepository.deleteById(id);
        this.recordImageService.deleteRecordImage(deleteRecord);
    }
}
