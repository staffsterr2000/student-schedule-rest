package com.staffsterr2000.studentschedulerest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class LectureDto {

    @JsonProperty("course")
    private CourseDto course;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("room")
    private Integer roomNumber;

//    @JsonProperty("audience")
//    private AudienceDto audience;

}
