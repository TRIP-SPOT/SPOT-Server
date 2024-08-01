package com.spot.spotserver.api.record.repository;

import com.spot.spotserver.api.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
