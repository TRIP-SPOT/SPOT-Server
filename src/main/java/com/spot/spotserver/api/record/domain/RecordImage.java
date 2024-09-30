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

    private String url;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;

    @Builder
    public RecordImage(String url, Record record) {
        this.url = url;
        this.record = record;
    }
}
