package com.staffsterr2000.studentschedulerest.controller.rest;

import com.staffsterr2000.studentschedulerest.dto.get.StudentGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.StudentPostDto;
import com.staffsterr2000.studentschedulerest.entity.Student;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/student")
@AllArgsConstructor
public class StudentRestController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    @ResponseBody
    public StudentGetDto getStudentById(@PathVariable("id") Long studentId) {
        Student studentById = studentService.getStudentById(studentId);
        return studentService.convertToStudentDto(studentById);
    }

    @GetMapping
    @ResponseBody
    public List<StudentGetDto> getStudents() {
        return studentService.getStudents().stream()
                .map(studentService::convertToStudentDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Object> createStudent(
            @Valid @RequestBody StudentPostDto studentPostDto, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach((error) -> {
                        String fieldName = ((FieldError) error).getField();
                        String message = error.getDefaultMessage();
                        errors.put(fieldName, message);
                    });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }


        Student student = studentService.convertToStudent(studentPostDto);
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(
                studentService.convertToStudentDto(createdStudent),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentGetDto> updateStudent(
            @PathVariable("id") Long studentId,
            @RequestBody StudentPostDto studentPostDto) {

        Student student = studentService.convertToStudent(studentPostDto);
        Student updatedStudent = studentService.updateStudent(studentId, student);

        return new ResponseEntity<>(
                studentService.convertToStudentDto(updatedStudent),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long studentId) {
        studentService.deleteStudent(studentId);
    }

}
