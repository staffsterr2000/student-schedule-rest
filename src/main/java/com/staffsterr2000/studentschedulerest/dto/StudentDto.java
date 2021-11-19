package com.staffsterr2000.studentschedulerest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {

    @JsonProperty("first name")
    private String firstName;

    @JsonProperty("last name")
    private String lastName;

    @JsonProperty("sgroup")
    private StudentGroupDto studentGroup;

}
