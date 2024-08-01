package com.spot.spotserver.api.spot.domain;

import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Work extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String posterUrl;
}
