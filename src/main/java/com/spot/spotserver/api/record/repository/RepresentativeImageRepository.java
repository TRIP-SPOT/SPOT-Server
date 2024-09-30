package com.spot.spotserver.api.record.repository;

import com.spot.spotserver.api.record.domain.RepresentativeImage;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepresentativeImageRepository extends JpaRepository<RepresentativeImage, Long> {
    Optional<RepresentativeImage> findByUserAndRegion(User user, Region region);
    List<RepresentativeImage> findAllByUser(User user);
}
