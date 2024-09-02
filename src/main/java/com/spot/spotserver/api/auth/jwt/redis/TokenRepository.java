package com.spot.spotserver.api.auth.jwt.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, String> {
}
