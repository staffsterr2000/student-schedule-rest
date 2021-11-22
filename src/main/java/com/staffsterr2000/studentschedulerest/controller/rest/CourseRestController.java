package com.staffsterr2000.studentschedulerest.controller.rest;

import com.staffsterr2000.studentschedulerest.dto.get.CourseGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.CoursePostDto;
import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.model.service.CourseService;
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
@RequestMapping("/api/v1/course")
@AllArgsConstructor
public class CourseRestController {

    private final CourseService courseService;

    @GetMapping("/{id}")
    @ResponseBody
    public CourseGetDto getCourseById(@PathVariable("id") Long courseId) {
        Course courseById = courseService.getCourseById(courseId);
        return courseService.convertToCourseDto(courseById);
    }

    @GetMapping
    @ResponseBody
    public List<CourseGetDto> getCourses() {
        return courseService.getCourses().stream()
                .map(courseService::convertToCourseDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Object> createCourse(
            @Valid @RequestBody CoursePostDto coursePostDto, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Course course = courseService.convertToCourse(coursePostDto);
        Course createdCourse = courseService.createCourse(course);

        return new ResponseEntity<>(
                courseService.convertToCourseDto(createdCourse),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseGetDto> updateCourse(
            @PathVariable("id") Long courseId,
            @RequestBody CoursePostDto coursePostDto) {

        Course course = courseService.convertToCourse(coursePostDto);
        Course updatedCourse = courseService.updateCourse(courseId, course);

        return new ResponseEntity<>(
                courseService.convertToCourseDto(updatedCourse),
                HttpStatus.OK
        );

    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") Long courseId) {
        courseService.deleteCourse(courseId);
    }

}
