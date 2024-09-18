package com.spot.spotserver.api.spot.domain;

import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="spot")
@Getter
@NoArgsConstructor
public class Spot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer contentId;

    private String name;

    private Region region;

    private City city;

    private Double longitude;

    private Double latitude;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;

    @Builder
    public Spot(Long id, Integer contentId, String name, Region region, City city, Double longitude, Double latitude, Work work) {
        this.id = id;
        this.contentId = contentId;
        this.name = name;
        this.region = region;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.work = work;
    }
}
