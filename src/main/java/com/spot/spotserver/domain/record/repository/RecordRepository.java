package com.spot.spotserver.domain.record.repository;

import com.spot.spotserver.domain.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
