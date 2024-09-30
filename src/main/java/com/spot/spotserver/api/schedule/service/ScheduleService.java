package com.spot.spotserver.api.schedule.service;

import com.spot.spotserver.api.schedule.domain.Location;
import com.spot.spotserver.api.schedule.domain.Schedule;
import com.spot.spotserver.api.schedule.domain.SelectedSpot;
import com.spot.spotserver.api.schedule.dto.request.*;
import com.spot.spotserver.api.schedule.dto.response.*;
import com.spot.spotserver.api.schedule.repository.LocationRepository;
import com.spot.spotserver.api.schedule.repository.ScheduleRepository;
import com.spot.spotserver.api.schedule.repository.SelectedSpotRepository;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.domain.SpotType;
import com.spot.spotserver.common.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final LocationRepository locationRepository;
    private final SelectedSpotRepository selectedSpotRepository;
    private final S3Service s3Service;

    private final static int REINDEXING_LENGTH = 10;

    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest scheduleRequest, User user) throws IOException {
        String imageUrl = this.s3Service.upload(scheduleRequest.getImage(), user.getNickname());
        Schedule newSchedule = Schedule.builder()
                .region(scheduleRequest.getRegion())
                .city(scheduleRequest.getCity())
                .startDate(scheduleRequest.getStartDate())
                .endDate(scheduleRequest.getEndDate())
                .image(imageUrl)
                .user(user)
                .build();
        Schedule savedSchedule = this.scheduleRepository.save(newSchedule);
        return new ScheduleResponse(savedSchedule);
    }

    public List<ScheduleResponse> getSchedules(User user) {
        return this.scheduleRepository.findAllByUser(user)
                .stream()
                .map(ScheduleResponse::new)
                .toList();
    }

    public DetailedScheduleResponse getSchedule(Long id) {
        Schedule schedule = this.scheduleRepository.findById(id).orElseThrow();
        List<LocationResponse> locationsResponses = this.locationRepository.findAllBySchedule(schedule)
                .stream()
                .map(LocationResponse::new)
                .toList();
        return new DetailedScheduleResponse(schedule, locationsResponses);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        this.scheduleRepository.deleteById(id);
    }

    @Transactional
    public ScheduleDurationUpdateResponse updateDuration(Long id, ScheduleDurationUpdateRequest scheduleDurationUpdateRequest) {
        Schedule schedule = this.scheduleRepository.findById(id).orElseThrow();
        schedule.updateDuration(scheduleDurationUpdateRequest.getStartDate(), scheduleDurationUpdateRequest.getEndDate());
        return new ScheduleDurationUpdateResponse(schedule);
    }

    @Transactional
    public ScheduleImageUpdateResponse updateImage(Long id, MultipartFile imageFile, User user) throws IOException {
        String image = this.s3Service.upload(imageFile, user.getNickname());
        Schedule schedule = this.scheduleRepository.findById(id).orElseThrow();
        schedule.updateImage(image);
        return new ScheduleImageUpdateResponse(image);
    }

    @Transactional
    public LocationResponse createLocation(LocationRequest locationRequest) {
        Schedule schedule = this.scheduleRepository.findById(locationRequest.getScheduleId()).orElseThrow();
        Double seq = Double.valueOf(this.locationRepository.countByScheduleAndDay(schedule, locationRequest.getDay()));

        Location newLocation = Location.builder()
                .day(locationRequest.getDay())
                .seq(seq)
                .name(locationRequest.getName())
                .description(locationRequest.getDescription())
                .schedule(schedule)
                .build();

        Location savedLocation = this.locationRepository.save(newLocation);
        return new LocationResponse(savedLocation);
    }

    @Transactional
    public void deleteLocations(List<Long> ids) {
        ids.forEach(this.locationRepository::deleteById);
    }

    public SelectedSpotsResponse getSelectedSpots(Long id) {
        Schedule schedule = this.scheduleRepository.findById(id).orElseThrow();
        List<SelectedSpot> selectedSpots = this.selectedSpotRepository.findAllBySchedule(schedule);

        List<SelectedSpotResponse> attractions = selectedSpots.stream()
                .filter(spot -> spot.getContentTypeId().equals(SpotType.ATTRACTION.type()))
                .map(SelectedSpotResponse::new)
                .toList();

        List<SelectedSpotResponse> restaurant = selectedSpots.stream()
                .filter(spot -> spot.getContentTypeId().equals(SpotType.RESTAURANT.type()))
                .map(SelectedSpotResponse::new)
                .toList();

        List<SelectedSpotResponse> accommodation = selectedSpots.stream()
                .filter(spot -> spot.getContentTypeId().equals(SpotType.ACCOMMODATION.type()))
                .map(SelectedSpotResponse::new)
                .toList();

        return new SelectedSpotsResponse(attractions, restaurant, accommodation);
    }

    @Transactional
    public void createSelectedSpot(Long id, List<SelectedSpotRequest> selectedSpotRequests) {
        Schedule schedule = this.scheduleRepository.findById(id).orElseThrow();
        List<SelectedSpot> selectedSpots = selectedSpotRequests
                .stream()
                .map((selectedSpotRequest ->
                    SelectedSpot.builder()
                            .title(selectedSpotRequest.getTitle())
                            .addr1(selectedSpotRequest.getAddr1())
                            .addr2(selectedSpotRequest.getAddr2())
                            .contentId(selectedSpotRequest.getContentId())
                            .contentTypeId(selectedSpotRequest.getContentTypeId())
                            .dist(selectedSpotRequest.getDist())
                            .image(selectedSpotRequest.getImage())
                            .schedule(schedule)
                            .build()
                ))
                .toList();

        this.selectedSpotRepository.saveAll(selectedSpots);
    }

    @Transactional
    public void updateLocationPosition(Long id, LocationPositionUpdateRequest locationPositionUpdateRequest) {
        Location location = this.locationRepository.findById(id).orElseThrow();
        Double middleSeq = locationPositionUpdateRequest.getMiddle();

        location.updatePosition(locationPositionUpdateRequest.getDay(), middleSeq);
    }
}
