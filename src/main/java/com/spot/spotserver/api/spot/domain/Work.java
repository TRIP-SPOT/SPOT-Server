package com.spot.spotserver.api.spot.domain;

import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="work")
@Getter
@NoArgsConstructor
public class Work extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String posterUrl;

    public Work(Long id, String name, String posterUrl) {
        this.id = id;
        this.name = name;
        this.posterUrl = posterUrl;
    }
}
