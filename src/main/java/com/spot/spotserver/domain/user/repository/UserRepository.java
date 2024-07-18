package com.spot.spotserver.domain.user.repository;

import com.spot.spotserver.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
