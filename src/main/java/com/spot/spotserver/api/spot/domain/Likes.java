package com.spot.spotserver.api.spot.domain;

import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "spot_id"})
})
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Likes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "spot_id")
    private Spot spot;

}
