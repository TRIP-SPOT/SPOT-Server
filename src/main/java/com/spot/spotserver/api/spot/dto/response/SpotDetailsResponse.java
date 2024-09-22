package com.spot.spotserver.api.spot.dto.response;


import com.spot.spotserver.api.spot.domain.Spot;
import com.spot.spotserver.api.spot.exception.SpotNotFoundException;
import com.spot.spotserver.api.spot.repository.LikesRepository;
import com.spot.spotserver.api.spot.repository.SpotRepository;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.payload.ErrorCode;

import java.util.Optional;

public record SpotDetailsResponse(
        String contentId,
        String contentTypeId,
        String title,
        String image,
        String homepage,
        String tel,
        String addr1,       // 주소
        String addr2,       // 상세주소
        String zipcode,     // 우편번호
        Integer region,
        Integer city,
        String longitude,
        String latitude,
        String overview,
        Boolean isLiked,
        Integer likeCount,
        String posterUrl
) {
    public static SpotDetailsResponse fromCommonInfo(CommonInfoResponse.Item item, SpotRepository spotRepository, LikesRepository likesRepository, User user) {

        Optional<Spot> spot = Optional.ofNullable(spotRepository.findByContentId(Integer.parseInt(item.contentid()))
                .orElseThrow(() -> new SpotNotFoundException(ErrorCode.SPOT_NOT_FOUND)));

        Boolean isLiked = likesRepository.findByUserAndSpot(user, spot.get()).isPresent();
        Integer likeCount = likesRepository.countBySpot(spot.get());

        return new SpotDetailsResponse(
                item.contentid(),
                item.contenttypeid(),
                item.title(),
                item.firstimage() != null ? item.firstimage() : item.firstimage2(), // 첫 번째 이미지가 없으면 대체 이미지 사용
                item.homepage(),
                item.tel(),
                item.addr1(),
                item.addr2(),
                item.zipcode(),
                spot.get().getRegion() != null ? spot.get().getRegion().ordinal() : null,
                spot.get().getCity() != null ? spot.get().getCity().ordinal() : null,
                item.mapx(),
                item.mapy(),
                item.overview(),
                isLiked,
                likeCount,
                spot.get().getWork().getPosterUrl()
        );
    }
}
