package com.staffsterr2000.studentschedulerest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LectureGetDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    private LocalDate localDate;

    @JsonProperty("course")
    private CourseDto course;

    @JsonProperty("audience")
    private AudienceDto audience;

}
