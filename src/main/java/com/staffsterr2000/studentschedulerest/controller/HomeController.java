package com.staffsterr2000.studentschedulerest.controller;

import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.entity.Student;
import com.staffsterr2000.studentschedulerest.model.service.CourseService;
import com.staffsterr2000.studentschedulerest.model.service.LectureService;
import com.staffsterr2000.studentschedulerest.model.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: "api/v1" link beginning

@RestController
@AllArgsConstructor
public class HomeController {


    private final StudentService studentService;

    private final CourseService courseService;

    private final LectureService lectureService;

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable("id") Long id) {
        return studentService.getStudent(id);
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/courses")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping("/lectures")
    public List<Lecture> getLectures() {
        return lectureService.getLectures();
    }

}
