package com.reactive.practice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ToString
public class PersonalInfo {

    private String idType; // 1 for SAID and 2 for Passport
    private String idNumber; // id or passport number
    private String firstName;
    private String surnameName;
    private String gender; // 0 for male 1 for female
    private Date dateOfBirth;
}
