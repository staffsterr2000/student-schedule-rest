package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.CourseGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGroupGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.CoursePostDto;
import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.model.repo.CourseRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepo courseRepository;
    private final LectureService lectureService;
    private final StudentGroupService studentGroupService;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseService(CourseRepo courseRepository,
                         @Lazy LectureService lectureService,
                         @Lazy StudentGroupService studentGroupService,
                         ModelMapper modelMapper) {

        this.courseRepository = courseRepository;
        this.lectureService = lectureService;
        this.studentGroupService = studentGroupService;
        this.modelMapper = modelMapper;
    }

    public CourseGetDto getCourseById(Long courseId) {
        Course courseById = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException(String.format("No such course with %d id.", courseId)));
        return convertToCourseDto(courseById);
    }

    public List<CourseGetDto> getCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToCourseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createCourse(CoursePostDto coursePostDto) {
        Course course = convertToCourse(coursePostDto);
        return courseRepository.save(course).getId();
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



    private CourseGetDto convertToCourseDto(Course course) {
        return modelMapper.map(course, CourseGetDto.class);
    }

    private Course convertToCourse(CoursePostDto coursePostDto) {
        CourseGetDto courseGetDto =
                transformAndFetchAllCourseDataToDto(coursePostDto);

        return modelMapper.map(courseGetDto, Course.class);
    }

    private CourseGetDto transformAndFetchAllCourseDataToDto(
            CoursePostDto coursePostDto) {

        CourseGetDto courseGetDto = new CourseGetDto();
        courseGetDto.setSubject(coursePostDto.getSubject());
        courseGetDto.setTeacherFullName(coursePostDto.getTeacherFullName());

        List<Long> lectureIds = coursePostDto.getLectureIds();
        if (lectureIds != null) {
            List<LectureGetDto> lectureGetDtos = lectureIds.stream()
                    .map(lectureService::getLectureById)
                    .collect(Collectors.toList());

            courseGetDto.setLectures(lectureGetDtos);
        }

        List<Long> studentGroupIds = coursePostDto.getStudentGroupIds();
        if (studentGroupIds != null) {
            List<StudentGroupGetDto> studentGroupGetDtos = studentGroupIds.stream()
                    .map(studentGroupService::getStudentGroupById)
                    .collect(Collectors.toList());

            courseGetDto.setStudentGroups(studentGroupGetDtos);

            // added
            studentGroupGetDtos.stream()
                    .map(StudentGroupGetDto::getCourses)
                    .forEach(list -> list.add(courseGetDto));

        }

        return courseGetDto;
    }

}
