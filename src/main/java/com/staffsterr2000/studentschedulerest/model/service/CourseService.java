package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.CourseDto;
import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.model.repo.CourseRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepo courseRepository;
    private final ModelMapper modelMapper;

    public CourseDto getCourseById(Long courseId) {
        Course courseById = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException(String.format("No such course with %d id.", courseId)));
        return convertToCourseDto(courseById);
    }

    public List<CourseDto> getCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToCourseDto)
                .collect(Collectors.toList());
    }

    // raw
    @Transactional
    public void createCourse(CourseDto courseDto) {
        Course course = convertToCourse(courseDto);
        courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        boolean courseExists =
                courseRepository.existsById(courseId);

        if (!courseExists) {
            throw new IllegalStateException(String.format("Course with id %d doesn't exist", courseId));
        }

        courseRepository.deleteById(courseId);
    }



    private CourseDto convertToCourseDto(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    private Course convertToCourse(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }

}
