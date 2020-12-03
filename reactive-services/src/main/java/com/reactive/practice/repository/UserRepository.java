package com.reactive.practice.repository;

import com.reactive.practice.model.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    @Query("{'personalInfo.idNumber': ?0, $or:[{'applicationStatus':{$ne:'EXPIRED'}}, {'applicationStatus':{$ne:'ARCHIVED'}}]}")
    Mono<User> findByIdNumber(String idNumber);

}
