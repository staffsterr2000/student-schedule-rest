package com.staffsterr2000.studentschedulerest.dto.get;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentGroupGetDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("courses")
    private List<CourseGetDto> courses;

    @JsonIgnore
    private List<StudentGetDto> students;

}
