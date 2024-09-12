package com.spot.spotserver.api.record.repository;

import com.spot.spotserver.api.record.domain.Record;
import com.spot.spotserver.api.record.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findAllByRegion(Region region);
}
