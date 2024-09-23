package com.spot.spotserver.api.spot.repository;

import com.spot.spotserver.api.spot.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByNameContainingIgnoreCase(String workName);
}
