package com.staffsterr2000.studentschedulerest.controller;

import com.staffsterr2000.studentschedulerest.dto.CourseDto;
import com.staffsterr2000.studentschedulerest.model.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@AllArgsConstructor
public class CourseRestController {

    private final CourseService courseService;

    @GetMapping("/{id}")
    @ResponseBody
    public CourseDto getCourseById(@PathVariable("id") Long courseId) {
        return courseService.getCourseById(courseId);
    }

    @GetMapping
    @ResponseBody
    public List<CourseDto> getCourses() {
        return courseService.getCourses();
    }

}
