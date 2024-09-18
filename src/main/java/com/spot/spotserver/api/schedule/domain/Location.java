package com.spot.spotserver.api.schedule.domain;

import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="location")
@Getter
@NoArgsConstructor
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private LocationType type;

    private Double longitude;

    private Double latitude;

    private Integer day;

    private Integer seq;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
