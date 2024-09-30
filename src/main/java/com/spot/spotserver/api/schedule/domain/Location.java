package com.spot.spotserver.api.schedule.domain;

import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
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

    private Integer day;

    private Double seq;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Builder
    public Location(String name, String description, Integer day, Double seq, Schedule schedule) {
        this.name = name;
        this.description = description;
        this.day = day;
        this.seq = seq;
        this.schedule = schedule;
    }

    public void updatePosition(Integer day, Double newSeq) {
        this.day = day;
        this.seq = newSeq;
    }

    public void updateSeq(Double seq) {
        this.seq = seq;
    }
}
