package com.spot.spotserver.api.record.repository;

import com.spot.spotserver.api.record.domain.RecordImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordImageRepository extends JpaRepository<RecordImage, Long> {
}
