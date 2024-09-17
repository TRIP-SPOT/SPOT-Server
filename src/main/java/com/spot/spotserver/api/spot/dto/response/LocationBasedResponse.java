package com.spot.spotserver.api.spot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record LocationBasedResponse(Response response) {

    public record Response(@JsonProperty("body") ResponseBody body) {
    }

    public record ResponseBody(@JsonProperty("items") Items items) {
    }

    public record Items(@JsonProperty("item") List<Item> item) {
    }

    public record Item(
            @JsonProperty("contentid") String contentid,
            @JsonProperty("contenttypeid") String contenttypeid,
            @JsonProperty("title") String title,
            @JsonProperty("firstimage") String firstimage,
            @JsonProperty("firstimage2") String firstimage2,
            @JsonProperty("addr1") String addr1,
            @JsonProperty("addr2") String addr2,
            @JsonProperty("areacode") String areacode,
            @JsonProperty("sigungucode") String sigungucode,
            @JsonProperty("tel") String tel,
            @JsonProperty("zipcode") String zipcode,
            @JsonProperty("mapx") String mapx,
            @JsonProperty("mapy") String mapy,
            @JsonProperty("mlevel") String mlevel,
            @JsonProperty("dist") String dist
    ) {
    }
}
