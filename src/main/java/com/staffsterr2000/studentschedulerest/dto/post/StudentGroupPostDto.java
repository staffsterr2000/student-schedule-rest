package com.staffsterr2000.studentschedulerest.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class StudentGroupPostDto {

    @NotBlank(message = "group name should be not blank")
    @Pattern(regexp = "[a-zA-Z0-9\\-]+", message = "use only latin letters, digits and '-' symbol")
    @JsonProperty("name")
    private String name;

    @JsonProperty("courseIds")
    private List<Long> courseIds;

    @JsonProperty("studentIds")
    private List<Long> studentIds;

    public void addCourseId(Long courseId) {
        if (courseIds == null) {
            courseIds = new ArrayList<>();
        }
        courseIds.add(courseId);
    }

    public void addStudentId(Long studentId) {
        if (studentIds == null) {
            studentIds = new ArrayList<>();
        }
        studentIds.add(studentId);
    }

}
