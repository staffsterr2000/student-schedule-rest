package com.staffsterr2000.studentschedulerest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.staffsterr2000.studentschedulerest.entity.Course.Subject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDto {

    @JsonProperty("subject")
    private Subject subject;

    @JsonProperty("teacher")
    private String teacher;

}
