package com.spot.spotserver.api.record.repository;

import com.spot.spotserver.api.record.domain.Record;
import com.spot.spotserver.api.record.domain.RecordImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordImageRepository extends JpaRepository<RecordImage, Long> {
    Optional<RecordImage> findFirstByRecordOrderByIdAsc(Record record);
}
