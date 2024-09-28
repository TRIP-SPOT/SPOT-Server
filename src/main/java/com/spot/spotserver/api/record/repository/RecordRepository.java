package com.spot.spotserver.api.record.repository;

import com.spot.spotserver.api.record.domain.Record;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findAllByUserAndRegion(User user, Region region);
}
