package com.reactive.practice.dataobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reactive.practice.model.PersonalInfo;
import com.reactive.practice.model.ValidationInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ToString
public class PersonalInfoRequest extends RequestBase implements Serializable {

    private ValidationInfo validationInfo;
    private PersonalInfo personalInfo;
}
