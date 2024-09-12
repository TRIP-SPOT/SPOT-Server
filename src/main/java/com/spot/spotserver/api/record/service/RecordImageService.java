package com.spot.spotserver.api.record.service;

import com.spot.spotserver.api.record.domain.Record;
import com.spot.spotserver.api.record.domain.RecordImage;
import com.spot.spotserver.api.record.repository.RecordImageRepository;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordImageService {
    private final RecordImageRepository recordImageRepository;
    private final S3Service s3Service;

    @Transactional
    public String createRecordImage(MultipartFile recordImage, Record record, User user) throws IOException {
        String imageUrl = this.s3Service.upload(recordImage, user.getNickname());
        RecordImage newRecordImage = RecordImage.builder()
                .url(imageUrl)
                .record(record)
                .build();
        this.recordImageRepository.save(newRecordImage);
        return imageUrl;
    }
}
