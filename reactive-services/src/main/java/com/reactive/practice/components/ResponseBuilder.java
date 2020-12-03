package com.reactive.practice.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactive.practice.dataobjects.ResponseMessages;
import com.reactive.practice.model.SessionInfo;
import com.reactive.practice.repository.SessionRepository;

import java.time.Instant;
import java.util.UUID;

public abstract class ResponseBuilder {

    protected ResponseMessages buildErrorResponse(String message, String code) {
        ResponseMessages responseMessages = new ResponseMessages();
        responseMessages.setCode(code);
        responseMessages.setMessage(message);
        responseMessages.setData(new ObjectMapper().createObjectNode());
        return responseMessages;
    }

    protected <T>ResponseMessages buildSuccessResponse(T type) {
        ResponseMessages responseMessages = new ResponseMessages();
        responseMessages.setCode("0000");
        responseMessages.setMessage("success");
        responseMessages.setData(type);
        return responseMessages;
    }

    protected String setupSecurity(String uid, SessionRepository sessionRepository) {
        String uuid = UUID.randomUUID().toString();
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setSid(uuid);
        sessionInfo.setUid(uid);
        sessionInfo.setCreated(Instant.now());
        sessionRepository.save(sessionInfo).subscribe();
        return uuid;
    }
}
