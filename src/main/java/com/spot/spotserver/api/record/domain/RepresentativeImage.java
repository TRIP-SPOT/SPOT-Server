package com.spot.spotserver.api.record.domain;

import com.spot.spotserver.api.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "representative_image")
@Getter
@NoArgsConstructor
public class RepresentativeImage {

    @GeneratedValue @Id
    private Long id;

    private String url;

    private Region region;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public RepresentativeImage(String url, Region region, User user) {
        this.url = url;
        this.region = region;
        this.user = user;
    }

    public void updateUrl(String url) {
        this.url = url;
    }
}
