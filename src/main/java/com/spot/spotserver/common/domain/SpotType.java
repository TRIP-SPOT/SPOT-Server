package com.spot.spotserver.common.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SpotType {
    ATTRACTION("12"),
    RESTAURANT("39"),
    ACCOMMODATION("32");

    private final String type;

    public String type() {return type;}
}
