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
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

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
        return this.sessionRepository.findBySid(personalInfoRequest.getId())
                .log()
                .flatMap(sessionInfo -> {
                    log.info("SessionInfo Found for given Session ID...");
                    return this.userRepository.findById(sessionInfo.getUid())
                            .flatMap(user -> {
                                log.info("Found User Info...");
                                return saveUserDetailsTwo(personalInfoRequest, personalInfoRequest.getId(), "", user);})
                            .switchIfEmpty(Mono.just(buildErrorResponse("User Not Found","40004")));
                })
                .switchIfEmpty(creatUser(personalInfoRequest))
                .onErrorReturn(buildErrorResponse("Something Went Wrong","4000"));//);
    }

    public Mono<ResponseMessages> creatUser(PersonalInfoRequest personalInfoRequest) {
        log.info("creatUser saveUserDetails...");
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setCreated(Instant.now());
        String id = ObjectId.get().toString();
        String uid = RandomStringUtils.randomAlphanumeric(10);
        sessionInfo.setSid(id);
        sessionInfo.setUid(uid);
        return validatorRepository.findById(personalInfoRequest.getId())
                .flatMap(validationInfo -> validatorRepository.delete(validationInfo))
                .then(Mono.just(sessionInfo).flatMap(sessionInfo2 -> sessionRepository.save(sessionInfo)))
                .then(saveUserDetails(personalInfoRequest, id, uid));
    }

    public Mono<ResponseMessages> saveUserDetails(PersonalInfoRequest personalInfoRequest, String sid, String uid) {
        log.info("Inside saveUserDetails...");
        return userRepository.findByIdNumber(personalInfoRequest.getPersonalInfo().getIdNumber())
                .map(user1 -> buildErrorResponse("User already exists","4001"))
                .switchIfEmpty(saveUserDetailsTwo(personalInfoRequest, sid, uid, new User()));
    }

    public Mono<ResponseMessages> saveUserDetailsTwo(PersonalInfoRequest personalInfoRequest, String sid, String uid, User user) {
        log.info("Inside saveUserDetailsTwo...");
        if (!uid.isEmpty()) {
            user.setId(uid);
        }
        user.setPersonalInfo(personalInfoRequest.getPersonalInfo());
        if (personalInfoRequest.getPersonalInfo().getIdNumber().length() <= 5
                && personalInfoRequest.getPersonalInfo().getIdNumber().length() >= 13) {
            return Mono.just(buildErrorResponse("ID Validation ","4002"));
        }
        //return this.userRepository.save(user).flatMap(user1 -> user1).map(r -> {};);
        return Mono.just(user).flatMap(user1 -> userRepository.save(user1)).thenReturn(buildSuccessResponse(sid));
    }
}
