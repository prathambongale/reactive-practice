package com.reactive.practice.repository;

import com.reactive.practice.model.ValidationInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidatorRepository extends ReactiveMongoRepository<ValidationInfo, String> {
}
