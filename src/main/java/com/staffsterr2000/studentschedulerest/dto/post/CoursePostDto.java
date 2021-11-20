package com.staffsterr2000.studentschedulerest.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.staffsterr2000.studentschedulerest.entity.Course.Subject;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CoursePostDto {

    @NotNull(message = "subject should be one of those: MATH, HISTORY or ENGLISH (for now)")
    @JsonProperty("subject")
    private Subject subject;

    @NotBlank(message = "teacher full name should be not blank")
    @Pattern(regexp = "^[A-Z][a-z]* [A-Z][a-z]*", message = "full name should consist at least two words with each begins with capital letter.")
    @JsonProperty("teacherFullName")
    private String teacherFullName;

    @JsonProperty("lectureIds")
    private List<Long> lectureIds;

    @JsonProperty("studentGroupIds")
    private List<Long> studentGroupIds;

    public void addLectureId(Long lectureId) {
        if (lectureIds == null) {
            lectureIds = new ArrayList<>();
        }
        lectureIds.add(lectureId);
    }

    public void addStudentGroupId(Long studentGroupId) {
        if (studentGroupIds == null) {
            studentGroupIds = new ArrayList<>();
        }
        studentGroupIds.add(studentGroupId);
    }

}
