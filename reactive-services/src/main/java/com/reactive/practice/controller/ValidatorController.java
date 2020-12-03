package com.reactive.practice.controller;

import com.reactive.practice.dataobjects.RequestBase;
import com.reactive.practice.dataobjects.ResponseMessages;
import com.reactive.practice.services.ValidatorService;
import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/validator", produces = MediaType.APPLICATION_JSON_VALUE)
@NoArgsConstructor
public class ValidatorController {

    private ValidatorService validatorService;

    @Autowired
    public ValidatorController(ValidatorService validatorService) { this.validatorService = validatorService; }

    @PostMapping(value = "/createValidatorString")
    @ApiOperation(tags = "Validator Management", value = "")
    public Mono<ResponseMessages> createValidatorString(@RequestBody RequestBase requestBase) {
        return validatorService.createValidatorString(requestBase);
    }
}
