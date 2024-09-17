package com.spot.spotserver.api.spot.client;

import com.spot.spotserver.api.spot.dto.response.CommonInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "commonInfoClient", url = "https://apis.data.go.kr/B551011/KorService1")
public interface CommonInfoClient {

    @GetMapping("/detailCommon1")
    CommonInfoResponse getCommonInfo(@RequestParam("MobileOS") String MobileOS,
                                     @RequestParam("MobileApp") String MobileApp,
                                     @RequestParam("_type") String _type,
                                     @RequestParam("contentId") String contentId,
                                     @RequestParam("defaultYN") String defaultYN,
                                     @RequestParam("firstImageYN") String firstImageYN,
                                     @RequestParam("addrinfoYN") String addrinfoYN,
                                     @RequestParam("mapinfoYN") String mapinfoYN,
                                     @RequestParam("overviewYN") String overviewYN,
                                     @RequestParam("serviceKey") String serviceKey
    );
}
