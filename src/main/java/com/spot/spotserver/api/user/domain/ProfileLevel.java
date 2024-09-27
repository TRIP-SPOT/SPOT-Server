package com.spot.spotserver.api.user.domain;

public enum ProfileLevel {
    BEGINNER("여행을 처음 시작한 단계"),
    VIEWER("미디어 속 장소에 관심을 가지기 시작한 단계"),
    FAN("미디어 촬영지 탐방을 처음 시작한 단계"),
    SCENE_EXPLORER("다양한 촬영지를 찾아 탐험하는 단계"),
    SPOT_TRAVELER("본격적으로 여러 곳을 여행하며 경험을 쌓는 단계"),
    SET_JETTER("촬영지 탐방에 깊이 빠져들어, 미디어 속 장소들을 찾아다니는 단계"),
    DIRECTORS_CUT("가장 상징적이고 중요한 촬영지를 모두 섭렵한 최상위 단계");

    private final String description;

    ProfileLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

