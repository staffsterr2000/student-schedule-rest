package com.staffsterr2000.studentschedulerest.controller.rest;

import com.staffsterr2000.studentschedulerest.dto.StudentDto;
import com.staffsterr2000.studentschedulerest.model.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@AllArgsConstructor
public class StudentRestController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    @ResponseBody
    public StudentDto getStudentById(@PathVariable("id") Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @GetMapping
    @ResponseBody
    public List<StudentDto> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public void createStudent(@RequestBody StudentDto student) {
        studentService.createStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long studentId) {
        studentService.deleteStudent(studentId);
    }

}
