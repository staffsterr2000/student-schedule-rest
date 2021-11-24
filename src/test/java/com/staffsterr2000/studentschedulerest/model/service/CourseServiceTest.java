package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.CourseGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGroupGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.CoursePostDto;
import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.entity.StudentGroup;
import com.staffsterr2000.studentschedulerest.model.repo.CourseRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private CourseRepo courseRepository;

    @MockBean
    private LectureService lectureService;

    @MockBean
    private StudentGroupService studentGroupService;

    @Test
    void shouldThrowExceptionIfGivenIdDoesNotExist() {
        Course course = new Course();

        Long existingId = 1L;
        Long fakeId = 2L;

        Mockito.doReturn(Optional.of(course))
                .when(courseRepository)
                .findById(existingId);

        // getCourseById method
        Assertions.assertThrows(IllegalStateException.class,
                () -> courseService.getCourseById(null));
        Assertions.assertThrows(IllegalStateException.class,
                () -> courseService.getCourseById(fakeId));

        // updateCourse method
        Assertions.assertThrows(IllegalStateException.class,
                () -> courseService.updateCourse(null, course));
        Assertions.assertThrows(IllegalStateException.class,
                () -> courseService.updateCourse(fakeId, course));

        // deleteCourse method
        Assertions.assertThrows(IllegalStateException.class,
                () -> courseService.deleteCourse(null));
        Assertions.assertThrows(IllegalStateException.class,
                () -> courseService.deleteCourse(fakeId));

    }

    @Test
    void shouldSetInverseDependenciesDuringCreating() {
        Course course = new Course();

        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setLocalDate(LocalDate.now().plusDays(1));
        List<Lecture> lectureList = new ArrayList<>(Arrays.asList(lecture));
        course.setLectures(lectureList);

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(1L);
        studentGroup.setName("NL17");
        studentGroup.setCourses(new ArrayList<>());
        List<StudentGroup> studentGroupList = new ArrayList<>(Arrays.asList(studentGroup));
        course.setStudentGroups(studentGroupList);

        Mockito.doReturn(course)
                .when(courseRepository)
                .save(course);

        courseService.createCourse(course);

        Assertions.assertTrue(lectureList.stream()
                .map(Lecture::getCourse)
                .map(course::equals)
                .reduce(true, (a, b) -> a && b));
        Assertions.assertTrue(studentGroupList.stream()
                .map(StudentGroup::getCourses)
                .map(list -> list.contains(course))
                .reduce(true, (a, b) -> a && b));

    }

    @Test
    void shouldSetDirectAndInverseDependenciesDuringUpdating() {
        Course course = new Course();
        course.setSubject(Course.Subject.MATH);
        course.setTeacherFullName("Eric Johnson");

        Long id = 1L;

        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setLocalDate(LocalDate.now().plusDays(1));
        List<Lecture> lectureList = new ArrayList<>(Arrays.asList(lecture));
        course.setLectures(lectureList);

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(1L);
        studentGroup.setName("NL17");
        studentGroup.setCourses(new ArrayList<>());
        List<StudentGroup> studentGroupList = new ArrayList<>(Arrays.asList(studentGroup));
        course.setStudentGroups(studentGroupList);

        Course emptyCourse = new Course();
        Mockito.doReturn(Optional.of(emptyCourse))
                .when(courseRepository)
                .findById(id);

        courseService.updateCourse(id, course);

        // direct
        Assertions.assertEquals(course, emptyCourse);

        // inverse
        Assertions.assertTrue(lectureList.stream()
                .map(Lecture::getCourse)
                .map(course::equals)
                .reduce(true, (a, b) -> a && b));
        Assertions.assertTrue(studentGroupList.stream()
                .map(StudentGroup::getCourses)
                .map(list -> list.contains(course))
                .reduce(true, (a, b) -> a && b));
    }

    @Test
    void shouldUnsetInverseDependenciesDuringDeleting() {
        Course course = new Course();
        course.setSubject(Course.Subject.MATH);
        course.setTeacherFullName("Eric Johnson");

        Long id = 1L;

        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setLocalDate(LocalDate.now().plusDays(1));
        lecture.setCourse(course);
        List<Lecture> lectureList = new ArrayList<>(Arrays.asList(lecture));
        course.setLectures(lectureList);

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(1L);
        studentGroup.setName("NL17");
        studentGroup.setCourses(new ArrayList<>(Arrays.asList(course, new Course())));
        List<StudentGroup> studentGroupList = new ArrayList<>(Arrays.asList(studentGroup));
        course.setStudentGroups(studentGroupList);

        Mockito.doReturn(Optional.of(course))
                .when(courseRepository)
                .findById(id);

        courseService.deleteCourse(id);

        Assertions.assertTrue(lectureList.stream()
                .map(Lecture::getCourse)
                .map(Objects::isNull)
                .reduce(true, (a, b) -> a && b));
        Assertions.assertTrue(studentGroupList.stream()
                .map(StudentGroup::getCourses)
                .map(list -> !list.contains(course))
                .reduce(true, (a, b) -> a && b));

    }

    @Test
    void shouldCorrectlyConvertEntityToDtoAndBackwards() {
        Course emptyFieldCourse = new Course();

        CourseGetDto emptyCourseGetDto = courseService
                .convertToCourseDto(emptyFieldCourse);

        CoursePostDto emptyCoursePostDto = new CoursePostDto();
        convertCourseGetDtoToPostDto(emptyCourseGetDto, emptyCoursePostDto);

        Course anotherEmptyFieldCourse = courseService
                .convertToCourse(emptyCoursePostDto);

        Assertions.assertEquals(emptyFieldCourse, anotherEmptyFieldCourse);

        // ---------------------------------------------------------------

        Course fullFieldCourse = new Course();
        fullFieldCourse.setId(1L);
        fullFieldCourse.setSubject(Course.Subject.MATH);
        fullFieldCourse.setTeacherFullName("John Jane");

        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setLocalDate(LocalDate.now().plusDays(1));
        List<Lecture> lectureList = new ArrayList<>(Arrays.asList(lecture));
        fullFieldCourse.setLectures(lectureList);

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(1L);
        studentGroup.setName("NL17");
        List<StudentGroup> studentGroupList = new ArrayList<>(Arrays.asList(studentGroup));
        fullFieldCourse.setStudentGroups(studentGroupList);

        lectureList.forEach(lectureFromList -> {
            Mockito.doReturn(lectureFromList)
                    .when(lectureService)
                    .getLectureById(lectureFromList.getId());
        });

        studentGroupList.forEach(studentGroupFromList -> {
            Mockito.doReturn(studentGroupFromList)
                    .when(studentGroupService)
                    .getStudentGroupById(studentGroupFromList.getId());
        });

        CourseGetDto fullCourseGetDto = courseService
                .convertToCourseDto(fullFieldCourse);

        CoursePostDto fullCoursePostDto = new CoursePostDto();
        convertCourseGetDtoToPostDto(fullCourseGetDto, fullCoursePostDto);

        Course anotherFullFieldCourse = courseService
                .convertToCourse(fullCoursePostDto);

        Assertions.assertEquals(fullFieldCourse, anotherFullFieldCourse);
        Assertions.assertEquals(lectureList, anotherFullFieldCourse.getLectures());
        Assertions.assertEquals(studentGroupList, anotherFullFieldCourse.getStudentGroups());

    }

    private void convertCourseGetDtoToPostDto(
            CourseGetDto courseGetDto, CoursePostDto coursePostDto) {

        Course.Subject subject = courseGetDto.getSubject();
        coursePostDto.setSubject(subject);

        String teacherName = courseGetDto.getTeacherFullName();
        coursePostDto.setTeacherFullName(teacherName);

        List<LectureGetDto> lectures = courseGetDto.getLectures();
        if (lectures != null) {
            List<Long> lectureIds = lectures.stream()
                    .map(LectureGetDto::getId)
                    .collect(Collectors.toList());

            coursePostDto.setLectureIds(lectureIds);
        }

        List<StudentGroupGetDto> studentGroups = courseGetDto.getStudentGroups();
        if (studentGroups != null) {
            List<Long> studentGroupIds = studentGroups.stream()
                    .map(StudentGroupGetDto::getId)
                    .collect(Collectors.toList());

            coursePostDto.setStudentGroupIds(studentGroupIds);
        }
    }
}