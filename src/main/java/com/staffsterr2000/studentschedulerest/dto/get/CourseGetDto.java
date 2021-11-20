package com.staffsterr2000.studentschedulerest.dto.get;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.staffsterr2000.studentschedulerest.entity.Course;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseGetDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("subject")
    private Course.Subject subject;

    @JsonProperty("teacherFullName")
    private String teacherFullName;

    // to rethink
    @JsonIgnore
    private List<LectureGetDto> lectures;

    @JsonIgnore
    private List<StudentGroupGetDto> studentGroups;

}
