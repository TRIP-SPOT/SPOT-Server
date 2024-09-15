package com.spot.spotserver.api.record.service;

import com.spot.spotserver.api.record.domain.RepresentativeImage;
import com.spot.spotserver.api.record.dto.RepresentativeImageRequest;
import com.spot.spotserver.api.record.dto.RepresentativeImageResponse;
import com.spot.spotserver.api.record.repository.RepresentativeImageRepository;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepresentativeImageService {
    private final RepresentativeImageRepository representativeImageRepository;
    private final S3Service s3Service;

    public RepresentativeImageResponse createRepresentativeImage(RepresentativeImageRequest representativeImageRequest, User user) throws IOException {
        String representativeImageUrl = this.s3Service.upload(representativeImageRequest.getImage(), user.getNickname());
        RepresentativeImage newRepresentativeImage = RepresentativeImage.builder().region(representativeImageRequest.getRegion())
                .url(representativeImageUrl)
                .user(user)
                .build();

        RepresentativeImage savedRepresentativeImage = this.representativeImageRepository.save(newRepresentativeImage);
        return new RepresentativeImageResponse(savedRepresentativeImage);
    }

    public List<RepresentativeImageResponse> getRepresentativeImages() {
        return this.representativeImageRepository.findAll()
                .stream()
                .map(RepresentativeImageResponse::new)
                .toList();
    }
}
