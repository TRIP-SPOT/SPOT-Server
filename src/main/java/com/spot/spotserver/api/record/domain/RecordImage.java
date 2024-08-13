package com.spot.spotserver.api.record.domain;

import jakarta.persistence.*;

@Entity
public class RecordImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private Boolean isRepresentative;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;
}
