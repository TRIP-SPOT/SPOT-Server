package com.spot.spotserver.api.user.domain;

import com.spot.spotserver.api.quiz.domain.Badge;
import com.spot.spotserver.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long socialId;

    private String email;

    private String nickname;

    private String profileUrl;

    private String color;

    private ProfileLevel profileLevel;

    @OneToMany(mappedBy = "user")
    private List<Badge> badges;

    public static User of(String email, Long socialId) {
        return User.builder()
                .email(email)
                .socialId(socialId)
                .build();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBadgeCount() {
        return badges.stream().mapToInt(Badge::getCount).sum();
    }

    public void updateProfileLevel() {
        int badgeCount = getBadgeCount();

        if (badgeCount <= 5) {
            this.profileLevel = ProfileLevel.BEGINNER;
        } else if (badgeCount <= 12) {
            this.profileLevel = ProfileLevel.VIEWER;
        } else if (badgeCount <= 20) {
            this.profileLevel = ProfileLevel.FAN;
        } else if (badgeCount <= 30) {
            this.profileLevel = ProfileLevel.SCENE_EXPLORER;
        } else if (badgeCount <= 40) {
            this.profileLevel = ProfileLevel.SPOT_TRAVELER;
        } else if (badgeCount <= 59) {
            this.profileLevel = ProfileLevel.SET_JETTER;
        } else {
            this.profileLevel = ProfileLevel.DIRECTORS_CUT;
        }
    }
}
