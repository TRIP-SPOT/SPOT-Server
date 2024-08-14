package com.spot.spotserver.api.record.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "record_image")
@Getter
@NoArgsConstructor
public class RecordImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private Boolean isRepresentative;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;

    @Builder
    public RecordImage(String imageUrl, Boolean isRepresentative, Record record) {
        this.imageUrl = imageUrl;
        this.isRepresentative = isRepresentative;
        this.record = record;
    }
}
