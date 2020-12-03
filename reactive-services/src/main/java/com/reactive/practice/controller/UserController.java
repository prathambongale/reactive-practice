package com.reactive.practice.controller;

import com.reactive.practice.dataobjects.PersonalInfoRequest;
import com.reactive.practice.dataobjects.ResponseMessages;
import com.reactive.practice.services.UserService;
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
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@NoArgsConstructor
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping(value = "/addUpdateUser")
    @ApiOperation(tags = "User Management", value = "")
    public Mono<ResponseMessages> addUpdateUser(@RequestBody PersonalInfoRequest personalInfoRequest) {
        return userService.addUpdateUser(personalInfoRequest);
    }
}
