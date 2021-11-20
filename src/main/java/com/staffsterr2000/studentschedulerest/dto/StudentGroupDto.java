package com.staffsterr2000.studentschedulerest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class StudentGroupDto {

    @JsonProperty("id")
    private Long studentGroupId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("courses")
    private List<CourseDto> courses = new ArrayList<>();

    @JsonIgnore
    private List<StudentDto> students = new ArrayList<>();

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
        courses.stream()
                .map(CourseDto::getStudentGroups)
                .forEach(list -> list.add(this));
    }

    public void addCourse(CourseDto course) {
        this.courses.add(course);
        course.getStudentGroups().add(this);
    }

//    public void setStudents(List<StudentDto> students) {
//        this.students = students;
//        students.forEach(student -> student.setStudentGroup(this));
//    }
//
//    public void addStudent(StudentDto student) {
//        this.students.add(student);
//        student.setStudentGroup(this);
//    }

}
