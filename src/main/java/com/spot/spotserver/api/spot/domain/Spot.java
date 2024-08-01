package com.spot.spotserver.api.spot.domain;

import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Spot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer contentId;

    private String name;

    private String imageUrl;

    private String address;

    private Double longitude;

    private Double latitude;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;
}
