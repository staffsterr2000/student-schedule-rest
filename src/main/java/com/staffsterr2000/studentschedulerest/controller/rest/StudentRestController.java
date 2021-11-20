package com.staffsterr2000.studentschedulerest.controller.rest;

import com.staffsterr2000.studentschedulerest.dto.get.StudentGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.StudentPostDto;
import com.staffsterr2000.studentschedulerest.model.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/student")
@AllArgsConstructor
public class StudentRestController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    @ResponseBody
    public StudentGetDto getStudentById(@PathVariable("id") Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @GetMapping
    @ResponseBody
    public List<StudentGetDto> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public ResponseEntity<Object> createStudent(
            @Valid @RequestBody StudentPostDto student, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach((error) -> {
                        String fieldName = ((FieldError) error).getField();
                        String message = error.getDefaultMessage();
                        errors.put(fieldName, message);
                    });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Long createdStudentId = studentService.createStudent(student);
        return new ResponseEntity<>(
                String.format("Successfully created audition with id %d", createdStudentId),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long studentId) {
        studentService.deleteStudent(studentId);
    }

}
