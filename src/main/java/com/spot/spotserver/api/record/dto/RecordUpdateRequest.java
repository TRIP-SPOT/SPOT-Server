package com.spot.spotserver.api.record.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class RecordUpdateRequest {
    String title;

    String description;

    List<String> deleteImages;

    List<MultipartFile> addImages;
}
