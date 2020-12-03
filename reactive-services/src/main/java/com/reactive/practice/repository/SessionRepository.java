package com.reactive.practice.repository;

import com.reactive.practice.model.SessionInfo;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SessionRepository extends ReactiveMongoRepository<SessionInfo, String> {

    @Query("{'sid': ?0}")
    Mono<SessionInfo> findBySid(String id);
}
