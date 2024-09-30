package com.spot.spotserver.api.spot.client;

import com.spot.spotserver.api.spot.dto.response.LocationBasedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "locationBasedListClient", url = "https://apis.data.go.kr/B551011/KorService1")
public interface LocationBasedListClient {

    @GetMapping("/locationBasedList1")
    LocationBasedResponse getLocationBasedList(@RequestParam("MobileOS") String MobileOS,
                                               @RequestParam("MobileApp") String MobileApp,
                                               @RequestParam("_type") String _type,
                                               @RequestParam("arrange") String arrange,
                                               @RequestParam("mapX") String mapX,
                                               @RequestParam("mapY") String mapY,
                                               @RequestParam("radius") String radius,
                                               @RequestParam("contentTypeId") String contentTypeId,
                                               @RequestParam("serviceKey") String serviceKey
    );
}
