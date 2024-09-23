package com.spot.spotserver.api.schedule.domain;

import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.domain.BaseEntity;
import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="schedule")
@Getter
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private Region region;

    @Enumerated(EnumType.ORDINAL)
    private City city;

    private LocalDate startDate;

    private LocalDate endDate;

    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Schedule(Long id, Region region, City city, LocalDate startDate, LocalDate endDate, String image, User user) {
        this.id = id;
        this.region = region;
        this.city = city;
        this.startDate = startDate;
        this.endDate = endDate;
        this.image = image;
        this.user = user;
    }

    public void updateDuration(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateImage(String image) {
        this.image = image;
    }
}
