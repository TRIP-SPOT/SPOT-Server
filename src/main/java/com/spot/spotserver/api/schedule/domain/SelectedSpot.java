package com.spot.spotserver.api.schedule.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="selected_spot")
@Getter
@NoArgsConstructor
public class SelectedSpot {
    @Id @GeneratedValue
    private Long id;

    private String title;

    private String addr1;

    private String addr2;

    private String contentId;

    private String contentTypeId;

    private String dist;

    private String image;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Builder
    public SelectedSpot(Long id, String title, String addr1, String addr2, String contentId, String contentTypeId, String dist, String image, Schedule schedule) {
        this.id = id;
        this.title = title;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.contentId = contentId;
        this.contentTypeId = contentTypeId;
        this.dist = dist;
        this.image = image;
        this.schedule = schedule;
    }
}
