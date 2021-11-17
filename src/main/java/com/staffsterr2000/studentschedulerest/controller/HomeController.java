package com.staffsterr2000.studentschedulerest.controller;

import com.staffsterr2000.studentschedulerest.entity.Student;
import com.staffsterr2000.studentschedulerest.model.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: "api/v1" link beginning

@RestController
@AllArgsConstructor
public class HomeController {

    private final StudentService studentService;


    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }



}
