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

    /**
     * Requirement:
     * ------------
     * Check if the session id is present in session collection.
     *
     * If Yes then update the user collection with new data that is passed in PersonalInfoRequest
     * and return the session ID and success code back in response.
     * Also validate the IDNumber for length if validation fails then return the CustomException.
     * (In this sample code I am only validating the IDNumber but later I need to validate each and every field pass in the request)
     *
     * If No that is - session ID is empty then create the new User.
     *
     *
     * Creat new user logic:
     * --------------------
     * -> While create a new user its mandatory to pass validator string. (create validator string using createValidatorString)
     * -> If the validator string exists in validator collection then delete that string.
     * -> Check if the IDNumber passed in PersonalInfo request exists in user DB.
     * If yes then send a Custom Message saying "Record for the given IDNumber exists". If No then continue.
     * -> Crete a new random session ID and user ID (_id in MongoDB) and save that in Session collection.
     * -> Validate the IDNumber for length if validation fails then return the CustomException.
     * (In this sample code I am only validating the IDNumber but later I need to validate each and every field pass in the request)
     * -> Write the new user record in User collection and return newly created session ID and success code back in response.
     *
     * */
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
