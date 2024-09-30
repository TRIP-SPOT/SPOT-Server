package com.spot.spotserver.api.spot.dto.response;

public record AroundDetailsResponse(
        String contentId,
        String contentTypeId,
        String title,
        String image,
        String homepage,
        String tel,
        String addr1,       // 주소
        String addr2,       // 상세주소
        String zipcode,     // 우편번호
        String longitude,
        String latitude,
        String overview
) {
    public static AroundDetailsResponse fromCommonInfo(CommonInfoResponse.Item item) {
        return new AroundDetailsResponse(
                item.contentid(),
                item.contenttypeid(),
                item.title(),
                item.firstimage() != null ? item.firstimage() : item.firstimage2(), // 첫 번째 이미지가 없으면 대체 이미지 사용
                item.homepage(),
                item.tel(),
                item.addr1(),
                item.addr2(),
                item.zipcode(),
                item.mapx(),
                item.mapy(),
                item.overview()
        );
    }
}
