package com.spot.spotserver.api.record.domain;

import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Record extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Region region;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
