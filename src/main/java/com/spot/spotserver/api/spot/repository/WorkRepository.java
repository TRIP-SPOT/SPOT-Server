package com.spot.spotserver.api.spot.repository;

import com.spot.spotserver.api.spot.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    @Query("SELECT w FROM Work w WHERE REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(w.name, ' ', ''), '!', ''), '?', ''), ',', ''), '-', ''), ':', '') " +
            "LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(:workName, ' ', ''), '!', ''), '?', ''), ',', ''), '-', ''), ':', ''), '%')")
    List<Work> searchWorksByName(String workName);
}
