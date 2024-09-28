package com.spot.spotserver.api.quiz.domain;

import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "badge")
@Getter
@NoArgsConstructor
public class Badge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private Region region;

    @Enumerated(EnumType.ORDINAL)
    private City city;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Badge(Long id, Region region, City city, User user) {
        this.id = id;
        this.region = region;
        this.city = city;
        this.user = user;
    }
}
