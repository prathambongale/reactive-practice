package com.reactive.practice.dataobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ToString
@Slf4j
public class ResponseMessages<T> implements Serializable {

    private Data<T> data;
    private String code;
    private String message;

    public T getData() { return this.data.getType(); }

    public void setData(T type) { this.data = new Data<>(type); }
}
