package com.spot.spotserver.api.user.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record ProfileImageRequest(
        MultipartFile profileImage
) {
}