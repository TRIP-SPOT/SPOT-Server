package com.spot.spotserver.api.record.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecordUpdateRequest {
    String title;

    String description;

    List<String> deleteImages;
}
