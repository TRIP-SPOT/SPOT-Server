package com.spot.spotserver.api.schedule.service;

import com.spot.spotserver.api.schedule.domain.Schedule;
import com.spot.spotserver.api.schedule.dto.ScheduleRequest;
import com.spot.spotserver.api.schedule.dto.ScheduleResponse;
import com.spot.spotserver.api.schedule.repository.ScheduleRepository;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
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

    @Transactional
    public void deleteSchedule(Long id) {
        this.scheduleRepository.deleteById(id);
    }
}
