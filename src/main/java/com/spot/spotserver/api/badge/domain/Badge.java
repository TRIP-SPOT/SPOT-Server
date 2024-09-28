package com.spot.spotserver.api.badge.domain;

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

    @Enumerated(EnumType.ORDINAL)
    private AcquisitionType acquisitionType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Badge(Region region, City city, AcquisitionType acquisitionType, User user) {
        this.region = region;
        this.city = city;
        this.acquisitionType = acquisitionType;
        this.user = user;
    }
}
