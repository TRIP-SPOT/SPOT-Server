package com.spot.spotserver.api.record.service;

import com.spot.spotserver.api.record.domain.RepresentativeImage;
import com.spot.spotserver.api.record.dto.RepresentativeImageRequest;
import com.spot.spotserver.api.record.dto.RepresentativeImageResponse;
import com.spot.spotserver.api.record.exception.RecordImageProcessingException;
import com.spot.spotserver.api.record.exception.RepresentativeImageNotFoundException;
import com.spot.spotserver.api.record.repository.RepresentativeImageRepository;
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
public class RepresentativeImageService {
    private final RepresentativeImageRepository representativeImageRepository;
    private final S3Service s3Service;

    @Transactional
    public RepresentativeImageResponse createRepresentativeImage(RepresentativeImageRequest representativeImageRequest, User user) throws IOException {
        try {
            String representativeImageUrl = this.s3Service.upload(representativeImageRequest.getImage(), user.getNickname());

            RepresentativeImage newRepresentativeImage = RepresentativeImage.builder().region(representativeImageRequest.getRegion())
                    .url(representativeImageUrl)
                    .user(user)
                    .build();

            RepresentativeImage savedRepresentativeImage = this.representativeImageRepository.save(newRepresentativeImage);
            return new RepresentativeImageResponse(savedRepresentativeImage);
        } catch (IOException e) {
            throw new RecordImageProcessingException(ErrorCode.RECORD_IMAGE_PROCESSING_FAILED);
        }
    }

    public List<RepresentativeImageResponse> getRepresentativeImages(User user) {
        return this.representativeImageRepository.findAllByUser(user)
                .stream()
                .map(RepresentativeImageResponse::new)
                .toList();
    }

    @Transactional
    public void updateRepresentativeImage(RepresentativeImageRequest representativeImageRequest, User user) throws IOException {
        try {
            String newImageUrl = this.s3Service.upload(representativeImageRequest.getImage(), user.getNickname());
            RepresentativeImage representativeImage = this.representativeImageRepository.findByUserAndRegion(user, representativeImageRequest.getRegion())
                    .orElseThrow(() -> new RepresentativeImageNotFoundException(ErrorCode.REPRESENTATIVE_IMAGE_NOT_FOUND));
            representativeImage.updateUrl(newImageUrl);
        } catch (IOException e) {
            throw new RecordImageProcessingException(ErrorCode.RECORD_IMAGE_PROCESSING_FAILED);
        }
    }
}
