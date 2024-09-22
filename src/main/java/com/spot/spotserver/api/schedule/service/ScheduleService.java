package com.spot.spotserver.api.schedule.service;

import com.spot.spotserver.api.schedule.domain.Location;
import com.spot.spotserver.api.schedule.domain.Schedule;
import com.spot.spotserver.api.schedule.dto.*;
import com.spot.spotserver.api.schedule.repository.LocationRepository;
import com.spot.spotserver.api.schedule.repository.ScheduleRepository;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final LocationRepository locationRepository;
    private final S3Service s3Service;

    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest scheduleRequest, MultipartFile image, User user) throws IOException {
        String imageUrl = this.s3Service.upload(image, user.getNickname());
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
        Integer day = this.locationRepository.countBySchedule(schedule);
        Integer seq = this.locationRepository.countByScheduleAndDay(schedule, day);

        Location newLocation = Location.builder()
                .day(day)
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
}
