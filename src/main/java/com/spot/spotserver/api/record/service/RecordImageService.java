package com.spot.spotserver.api.record.service;

import com.spot.spotserver.api.record.domain.Record;
import com.spot.spotserver.api.record.domain.RecordImage;
import com.spot.spotserver.api.record.exception.RecordImageNotFoundException;
import com.spot.spotserver.api.record.exception.RecordImageProcessingException;
import com.spot.spotserver.api.record.repository.RecordImageRepository;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.payload.ErrorCode;
import com.spot.spotserver.common.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordImageService {
    private final RecordImageRepository recordImageRepository;
    private final S3Service s3Service;

    @Transactional
    public String createRecordImage(MultipartFile recordImage, Record record, User user) throws IOException {
        try {
            String imageUrl = this.s3Service.upload(recordImage, user.getNickname());
            RecordImage newRecordImage = RecordImage.builder()
                    .url(imageUrl)
                    .record(record)
                    .build();
            this.recordImageRepository.save(newRecordImage);
            return imageUrl;
        } catch (IOException e) {
            throw new RecordImageProcessingException(ErrorCode.RECORD_IMAGE_PROCESSING_FAILED);
        }
    }

    public String getRegionalThumbnailImage(Record record) {
        RecordImage thumbnailImage = this.recordImageRepository.findFirstByRecordOrderByIdAsc(record)
                .orElseThrow(() -> new RecordImageNotFoundException(ErrorCode.RECORD_IMAGE_NOT_FOUND));
        return thumbnailImage.getUrl();
    }

    public List<String> getRecordImages(Record record) {
        List<RecordImage> recordImages = this.recordImageRepository.findAllByRecord(record);
        if (recordImages.isEmpty()) {
            throw new RecordImageNotFoundException(ErrorCode.RECORD_IMAGE_NOT_FOUND);
        }
        return recordImages.stream().map((RecordImage::getUrl)).toList();
    }

    @Transactional
    public void deleteRecordImageById(Long recordImageId) {
        if (!this.recordImageRepository.existsById(recordImageId)) {
            throw new RecordImageNotFoundException(ErrorCode.RECORD_IMAGE_NOT_FOUND);
        }
        this.recordImageRepository.deleteById(recordImageId);
    }

    @Transactional
    public void deleteRecordImage(Record record) {
        List<RecordImage> recordImages = this.recordImageRepository.findAllByRecord(record);
        if (recordImages.isEmpty()) {
            throw new RecordImageNotFoundException(ErrorCode.RECORD_IMAGE_NOT_FOUND);
        }
        this.recordImageRepository.deleteAllByRecord(record);
    }
}
