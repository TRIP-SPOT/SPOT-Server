package com.spot.spotserver.api.record.domain;

import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "record")
@Getter
@NoArgsConstructor
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

    @Builder
    public Record(String name, String description, Region region, User user) {
        this.name = name;
        this.description = description;
        this.region = region;
        this.user = user;
    }
}
