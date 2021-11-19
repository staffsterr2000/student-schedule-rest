package com.staffsterr2000.studentschedulerest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StudentGroupDto {

    @JsonProperty("name")
    private String groupName;

    @JsonProperty("courses")
    private List<CourseDto> courses;

}
