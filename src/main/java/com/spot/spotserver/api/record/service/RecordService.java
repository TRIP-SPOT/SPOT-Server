package com.spot.spotserver.api.record.service;

import com.spot.spotserver.api.record.domain.Record;
import com.spot.spotserver.api.record.domain.Region;
import com.spot.spotserver.api.record.dto.RecordRequest;
import com.spot.spotserver.api.record.dto.RecordResponse;
import com.spot.spotserver.api.record.dto.RegionalRecordResponse;
import com.spot.spotserver.api.record.repository.RecordRepository;
import com.spot.spotserver.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {
    private final RecordRepository recordRepository;
    private final RecordImageService recordImageService;

    @Transactional
    public RecordResponse createRecord(RecordRequest recordRequest, List<MultipartFile> images, User user) {
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

        List<String> imageUrls = images.stream().map((image) -> {
            try {
                return recordImageService.createRecordImage(image, newRecord, user);
            } catch (IOException e) {
                throw new RuntimeException(e);
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
}
