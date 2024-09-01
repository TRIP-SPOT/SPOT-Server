package com.spot.spotserver.api.record.domain;

import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Record(String name, String description, Region region, LocalDateTime startDate, LocalDateTime endDate, User user) {
        this.name = name;
        this.description = description;
        this.region = region;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
    }
}
