package com.staffsterr2000.studentschedulerest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class LectureDto {

    @JsonProperty("date")
    private LocalDate localDate;

    @JsonProperty("courseId")
    private Long courseId;

    @JsonProperty("audienceId")
    private Long audienceId;

//    public void setCourse(CourseDto course) {
//        this.course = course;
//        course.getLectures().add(this);
//    }

}
