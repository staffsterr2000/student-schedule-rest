package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.CourseGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGroupGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.CoursePostDto;
import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.entity.StudentGroup;
import com.staffsterr2000.studentschedulerest.model.repo.CourseRepo;
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

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException(String.format("No such course with %d id.", courseId)));
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Transactional
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public void updateCourse(Long courseId, Course course) {
//        Course courseFromDb = courseRepository.findById(courseId)
//                .orElseThrow(() -> new IllegalStateException(
//                        String.format("Course with id %d doesn't exist", courseId)
//                ));
//
//        Course.Subject subject = coursePostDto.getSubject();
//        if (subject != null) {
//            courseFromDb.setSubject(subject);
//        }
//
//        String teacherFullName = coursePostDto.getTeacherFullName();
//        if (teacherFullName != null && !teacherFullName.isEmpty()) {
//            courseFromDb.setTeacherFullName(teacherFullName);
//        }
//
//        List<Student> students = coursePostDto.get
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        boolean courseExists = courseRepository.existsById(courseId);

        if (!courseExists) {
            throw new IllegalStateException(String.format("Course with id %d doesn't exist", courseId));
        }

        courseRepository.deleteById(courseId);
    }



    public CourseGetDto convertToCourseDto(Course course) {
        return modelMapper.map(course, CourseGetDto.class);
    }

    public Course convertToCourse(CoursePostDto coursePostDto) {
        Course course = new Course();
        course.setSubject(coursePostDto.getSubject());
        course.setTeacherFullName(coursePostDto.getTeacherFullName());

        List<Long> lectureIds = coursePostDto.getLectureIds();
        if (lectureIds != null) {
            List<Lecture> lectures = lectureIds.stream()
                    .map(lectureService::getLectureById)
                    .collect(Collectors.toList());
            course.setLectures(lectures);
        }

        List<Long> studentGroupIds = coursePostDto.getStudentGroupIds();
        if (studentGroupIds != null) {
            List<StudentGroup> studentGroups = studentGroupIds.stream()
                    .map(studentGroupService::getStudentGroupById)
                    .collect(Collectors.toList());
            course.setStudentGroups(studentGroups);

            // added
            studentGroups.stream()
                    .map(StudentGroup::getCourses)
                    .forEach(list -> list.add(course));

        }

        return course;
    }

}
