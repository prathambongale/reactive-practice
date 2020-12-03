package com.reactive.practice.services;

import com.reactive.practice.components.ResponseBuilder;
import com.reactive.practice.dataobjects.PersonalInfoRequest;
import com.reactive.practice.dataobjects.ResponseMessages;
import com.reactive.practice.model.SessionInfo;
import com.reactive.practice.model.User;
import com.reactive.practice.repository.SessionRepository;
import com.reactive.practice.repository.UserRepository;
import com.reactive.practice.repository.ValidatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserService extends ResponseBuilder {

    private final UserRepository userRepository;
    private final ValidatorRepository validatorRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public UserService(UserRepository userRepository, ValidatorRepository validatorRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.validatorRepository = validatorRepository;
        this.sessionRepository = sessionRepository;
    }

    public Mono<ResponseMessages> addUpdateUser(PersonalInfoRequest personalInfoRequest) {
        return sessionRepository.findBySid(personalInfoRequest.getId())
                .log()
                .flatMap(sessionInfo -> userRepository.findById(sessionInfo.getUid())
                        .flatMap(user -> saveUserDetailsTwo(personalInfoRequest, personalInfoRequest.getId()))
                .switchIfEmpty(creatUser(personalInfoRequest)));
    }

    public Mono<ResponseMessages> creatUser(PersonalInfoRequest personalInfoRequest) {
        SessionInfo sessionInfo = new SessionInfo();
        String id = "";
        return validatorRepository.findById(personalInfoRequest.getId())
                .flatMap(validationInfo -> validatorRepository.delete(validationInfo))
                .then(Mono.just(sessionInfo).flatMap(sessionInfo2 -> sessionRepository.save(sessionInfo)))
                .then(saveUserDetails(personalInfoRequest, id));
    }

    public Mono<ResponseMessages> saveUserDetails(PersonalInfoRequest personalInfoRequest, String id) {
        return userRepository.findByIdNumber(personalInfoRequest.getPersonalInfo().getIdNumber())
                .map(user1 -> buildErrorResponse("User already exists","4001"))
                .switchIfEmpty(saveUserDetailsTwo(personalInfoRequest, id));
    }

    public Mono<ResponseMessages> saveUserDetailsTwo(PersonalInfoRequest personalInfoRequest, String id) {
        User user = new User();
        user.setPersonalInfo(personalInfoRequest.getPersonalInfo());
        if (personalInfoRequest.getPersonalInfo().getIdNumber().length() >= 5
                && personalInfoRequest.getPersonalInfo().getIdNumber().length() <= 13) {
            return Mono.just(buildErrorResponse("",""));
        }
        return this.userRepository.save(user).thenReturn(buildSuccessResponse(id));
    }
}
