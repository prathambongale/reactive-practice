package com.reactive.practice.services;

import com.reactive.practice.components.ResponseBuilder;
import com.reactive.practice.dataobjects.RequestBase;
import com.reactive.practice.dataobjects.ResponseMessages;
import com.reactive.practice.model.ValidationInfo;
import com.reactive.practice.repository.ValidatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@Slf4j
public class ValidatorService extends ResponseBuilder {

    private final ValidatorRepository validatorRepository;

    @Autowired
    public ValidatorService(ValidatorRepository validatorRepository) {
        this.validatorRepository = validatorRepository;
    }

    public Mono<ResponseMessages> createValidatorString(RequestBase requestBase) {
        ValidationInfo validationInfo = new ValidationInfo();
        validationInfo.setId(requestBase.getId());
        validationInfo.setValidator(RandomStringUtils.randomAlphabetic(6).toLowerCase());
        validationInfo.setCreated(Instant.now());
        return this.validatorRepository.save(validationInfo).thenReturn(buildSuccessResponse(validationInfo));
    }
}
