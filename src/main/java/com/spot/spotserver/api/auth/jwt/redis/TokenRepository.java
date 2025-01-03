package com.spot.spotserver.api.auth.jwt.redis;

import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, String> {
}
