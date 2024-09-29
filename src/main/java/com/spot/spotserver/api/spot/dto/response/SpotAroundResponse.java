package com.spot.spotserver.api.spot.dto.response;

import java.util.List;
import java.util.stream.Collectors;

public record SpotAroundResponse(
        List<Place> attraction,
        List<Place> restaurant,
        List<Place> accommodation
) {
    public record Place(
            String title,
            String addr1,
            String addr2,
            String contentId,
            String contentTypeId,
            String dist,
            String image,
            String longitude,
            String latitude
    ) {
        public static Place fromLocationBasedList(LocationBasedResponse.Item item) {
            return new Place(
                    item.title(),
                    item.addr1(),
                    item.addr2(),
                    item.contentid(),
                    item.contenttypeid(),
                    item.dist(),
                    item.firstimage() != null ? item.firstimage() : item.firstimage2(),
                    item.mapx(),
                    item.mapy()
            );
        }
    }

    public static SpotAroundResponse fromLocationBasedList(LocationBasedResponse attractionResponse,
                                                           LocationBasedResponse restaurantResponse,
                                                           LocationBasedResponse accommodationResponse) {
        List<Place> attractions = convertToPlaces(attractionResponse);
        List<Place> restaurants = convertToPlaces(restaurantResponse);
        List<Place> accommodations = convertToPlaces(accommodationResponse);

        return new SpotAroundResponse(attractions, restaurants, accommodations);
    }

    private static List<Place> convertToPlaces(LocationBasedResponse response) {
        return response.response().body().items().item().stream()
                .limit(6)
                .skip(1)
                .map(Place::fromLocationBasedList)
                .collect(Collectors.toList());
    }
}
