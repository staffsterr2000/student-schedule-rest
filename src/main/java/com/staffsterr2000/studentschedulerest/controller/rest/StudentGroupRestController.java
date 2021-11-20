package com.staffsterr2000.studentschedulerest.controller.rest;

import com.staffsterr2000.studentschedulerest.dto.get.StudentGroupGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.StudentGroupPostDto;
import com.staffsterr2000.studentschedulerest.model.service.StudentGroupService;
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
@RequestMapping("/api/v1/sgroup")
@AllArgsConstructor
public class StudentGroupRestController {

    private final StudentGroupService studentGroupService;

    @GetMapping("/{id}")
    @ResponseBody
    public StudentGroupGetDto getStudentGroupById(@PathVariable("id") Long studentGroupId) {
        return studentGroupService.getStudentGroupById(studentGroupId);
    }

    @GetMapping
    @ResponseBody
    public List<StudentGroupGetDto> getStudentGroups() {
        return studentGroupService.getStudentGroups();
    }

    @PostMapping
    public ResponseEntity<Object> createStudentGroup(
            @Valid @RequestBody StudentGroupPostDto studentGroup, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Long createdStudentGroupId = studentGroupService.createStudentGroup(studentGroup);
        return new ResponseEntity<>(
                String.format("Successfully created group with id %d", createdStudentGroupId),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public void deleteStudentGroup(@PathVariable("id") Long studentGroupId) {
        studentGroupService.deleteStudentGroup(studentGroupId);
    }

}
