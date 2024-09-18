package com.spot.spotserver.api.schedule.domain;

import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.domain.BaseEntity;
import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import jakarta.persistence.*;
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

    private Region region;

    private City city;

    private LocalDate startDate;

    private LocalDate endDate;

    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
