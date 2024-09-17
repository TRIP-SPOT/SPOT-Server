package com.spot.spotserver.api.quiz.domain;

import com.spot.spotserver.api.record.domain.Region;
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

    private Region region;

    private Integer count = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Badge(Long id, Region region, User user) {
        this.id = id;
        this.region = region;
        this.user = user;
    }

    public void addBadge() {
        this.count++;
    }
}
