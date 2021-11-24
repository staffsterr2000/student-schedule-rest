package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.CourseGetDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Course with id %d doesn't exist", courseId)));
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Transactional
    public Course createCourse(Course course) {
        Course savedCourse = courseRepository.save(course);

        List<Lecture> lectures = savedCourse.getLectures();
        if (lectures != null) {
            lectures.forEach(lecture -> lecture.setCourse(savedCourse));
        }

        List<StudentGroup> studentGroups = savedCourse.getStudentGroups();
        if (studentGroups != null) {
            studentGroups.stream()
                    .filter(studentGroup -> studentGroup.getCourses() == null)
                    .forEach(studentGroup -> studentGroup.setCourses(new ArrayList<>()));

            studentGroups.stream()
                    .map(StudentGroup::getCourses)
                    .forEach(list -> list.add(savedCourse));
        }

        return savedCourse;
    }

    @Transactional
    public Course updateCourse(Long courseId, Course modifiedCourse) {
        Course courseFromDb = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Course with id %d doesn't exist", courseId)
                ));

        Course.Subject modifiedSubject = modifiedCourse.getSubject();
        if (modifiedSubject != null) {
            courseFromDb.setSubject(modifiedSubject);
        }

        String modifiedTeacherFullName = modifiedCourse.getTeacherFullName();
        if (modifiedTeacherFullName != null && !modifiedTeacherFullName.isEmpty()) {
            courseFromDb.setTeacherFullName(modifiedTeacherFullName);
        }

        List<StudentGroup> modifiedStudentGroups = modifiedCourse.getStudentGroups();
        if (modifiedStudentGroups != null) {
            courseFromDb.setStudentGroups(modifiedStudentGroups);

            modifiedStudentGroups.stream()
                    .filter(studentGroup -> studentGroup.getCourses() == null)
                    .forEach(studentGroup -> studentGroup.setCourses(new ArrayList<>()));

            // not working alternative to ^
//            modifiedStudentGroups.stream()
//                    .map(StudentGroup::getCourses)
//                    .filter(Objects::isNull)
//                    .forEach(list -> list = new ArrayList<>());

            modifiedStudentGroups.stream()
                    .map(StudentGroup::getCourses)
                    .filter(list -> !list.contains(courseFromDb))
                    .forEach(list -> list.add(courseFromDb));

        }

        List<Lecture> modifiedLectures = modifiedCourse.getLectures();
        if (modifiedLectures != null) {
            courseFromDb.setLectures(modifiedLectures);

            modifiedLectures.forEach(lecture ->
                    lecture.setCourse(courseFromDb));
        }

        return courseRepository.save(courseFromDb);
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        Course courseFromDb = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Course with id %d doesn't exist", courseId)
                ));

        List<StudentGroup> studentGroups = courseFromDb.getStudentGroups();
        if (studentGroups != null) {
            studentGroups.stream()
                    .map(StudentGroup::getCourses)
                    .forEach(list -> list.remove(courseFromDb));
            courseFromDb.setStudentGroups(null);
        }

        List<Lecture> lectures = courseFromDb.getLectures();
        if (lectures != null) {
            lectures.forEach(lecture -> lecture.setCourse(null));
            courseFromDb.setLectures(null);
        }

        courseRepository.delete(courseFromDb);
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
        }

        return course;
    }

}
