package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.model.repo.CourseRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepo courseRepository;


    public void createCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

}
