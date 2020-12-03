package com.reactive.practice.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class CustomeException extends Exception{

    private String code;
    private String message;

    public CustomeException(String message) {
        this.message = message;
    }
}
