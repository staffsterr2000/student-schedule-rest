package com.staffsterr2000.studentschedulerest.dto.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LectureGetDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("localDate")
    private LocalDate localDate;

    @JsonProperty("course")
    private CourseGetDto course;

    @JsonProperty("audience")
    private AudienceGetDto audience;

}
