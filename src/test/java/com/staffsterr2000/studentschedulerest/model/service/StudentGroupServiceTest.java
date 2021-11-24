package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.CourseGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGroupGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.StudentGroupPostDto;
import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.entity.Student;
import com.staffsterr2000.studentschedulerest.entity.StudentGroup;
import com.staffsterr2000.studentschedulerest.model.repo.StudentGroupRepo;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class StudentGroupServiceTest {

    @Autowired
    private StudentGroupService studentGroupService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private StudentGroupRepo studentGroupRepository;

    @MockBean
    private StudentService studentService;

    @MockBean
    private CourseService courseService;

    @Test
    void shouldThrowExceptionIfGivenIdDoesNotExist() {
        StudentGroup studentGroup = new StudentGroup();

        Long existingId = 1L;
        Long fakeId = 2L;

        Mockito.doReturn(Optional.of(studentGroup))
                .when(studentGroupRepository)
                .findById(existingId);

        // getStudentGroupById method
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentGroupService.getStudentGroupById(null));
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentGroupService.getStudentGroupById(fakeId));

        // updateStudentGroup method
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentGroupService.updateStudentGroup(null, studentGroup));
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentGroupService.updateStudentGroup(fakeId, studentGroup));

        // deleteStudentGroup method
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentGroupService.deleteStudentGroup(null));
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentGroupService.deleteStudentGroup(fakeId));

    }

    @Test
    void shouldThrowExceptionOnAlreadyExistingGroupNameSetting() {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(1L);
        studentGroup.setName("KN17");

        Mockito.doReturn(true)
                .when(studentGroupRepository)
                .existsByName(studentGroup.getName());

        Assertions.assertThrows(IllegalStateException.class,
                () -> studentGroupService.createStudentGroup(studentGroup));
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentGroupService.updateStudentGroup(studentGroup.getId(), studentGroup));

    }

    @Test
    void shouldSetInverseDependenciesDuringCreating() {
        StudentGroup studentGroup = new StudentGroup();

        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Kimoh");
        student.setLastName("Titatau");
        List<Student> studentList = new ArrayList<>(Arrays.asList(student));
        studentGroup.setStudents(studentList);

        Course course = new Course();
        course.setId(1L);
        course.setSubject(Course.Subject.MATH);
        course.setTeacherFullName("Nowadays Hero");
        course.setStudentGroups(new ArrayList<>());
        List<Course> courseList = new ArrayList<>(Arrays.asList(course));
        studentGroup.setCourses(courseList);

        Mockito.doReturn(studentGroup)
                .when(studentGroupRepository)
                .save(studentGroup);

        studentGroupService.createStudentGroup(studentGroup);

        Assertions.assertTrue(studentList.stream()
                .map(Student::getStudentGroup)
                .map(studentGroup::equals)
                .reduce(true, (a, b) -> a && b));
        Assertions.assertTrue(courseList.stream()
                .map(Course::getStudentGroups)
                .map(list -> list.contains(studentGroup))
                .reduce(true, (a, b) -> a && b));

    }

    @Test
    void shouldSetDirectAndInverseDependenciesDuringUpdating() {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setName("KR17-1");

        Long id = 1L;

        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Kimoh");
        student.setLastName("Titatau");
        List<Student> studentList = new ArrayList<>(Arrays.asList(student));
        studentGroup.setStudents(studentList);

        Course course = new Course();
        course.setId(1L);
        course.setSubject(Course.Subject.MATH);
        course.setTeacherFullName("Nowadays Hero");
        course.setStudentGroups(new ArrayList<>());
        List<Course> courseList = new ArrayList<>(Arrays.asList(course));
        studentGroup.setCourses(courseList);

        StudentGroup emptyStudentGroup = new StudentGroup();
        Mockito.doReturn(Optional.of(emptyStudentGroup))
                .when(studentGroupRepository)
                .findById(id);

        studentGroupService.updateStudentGroup(id, studentGroup);

        // direct
        Assertions.assertEquals(studentGroup, emptyStudentGroup);

        // inverse
        Assertions.assertTrue(studentList.stream()
                .map(Student::getStudentGroup)
                .map(studentGroup::equals)
                .reduce(true, (a, b) -> a && b));
        Assertions.assertTrue(courseList.stream()
                .map(Course::getStudentGroups)
                .map(list -> list.contains(studentGroup))
                .reduce(true, (a, b) -> a && b));

    }

    // TODO: implement deletion test logic
    @Test
    void shouldSuccessfullyDeleteEntityFromDb() {

    }

    @Test
    void shouldCorrectlyConvertEntityToDtoAndBackwards() {
        StudentGroup emptyFieldStudentGroup = new StudentGroup();

        StudentGroupGetDto emptyStudentGroupGetDto = studentGroupService
                .convertToStudentGroupDto(emptyFieldStudentGroup);

        StudentGroupPostDto emptyStudentGroupPostDto = new StudentGroupPostDto();
        convertStudentGroupGetDtoToPostDto(emptyStudentGroupGetDto, emptyStudentGroupPostDto);

        StudentGroup anotherEmptyFieldStudentGroup = studentGroupService
                .convertToStudentGroup(emptyStudentGroupPostDto);

        Assertions.assertEquals(emptyFieldStudentGroup, anotherEmptyFieldStudentGroup);

        // ---------------------------------------------------------------

        StudentGroup fullFieldStudentGroup = new StudentGroup();
        fullFieldStudentGroup.setId(1L);
        fullFieldStudentGroup.setName("KM17");

        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Kimoh");
        student.setLastName("Titatau");
        List<Student> studentList = new ArrayList<>(Arrays.asList(student));
        fullFieldStudentGroup.setStudents(studentList);

        Course course = new Course();
        course.setId(1L);
        course.setSubject(Course.Subject.MATH);
        course.setTeacherFullName("Nowadays Hero");
        List<Course> courseList = new ArrayList<>(Arrays.asList(course));
        fullFieldStudentGroup.setCourses(courseList);

        studentList.forEach(studentFromList -> {
            Mockito.doReturn(studentFromList)
                    .when(studentService)
                    .getStudentById(studentFromList.getId());
        });

        courseList.forEach(courseFromList -> {
            Mockito.doReturn(courseFromList)
                    .when(courseService)
                    .getCourseById(courseFromList.getId());
        });

        StudentGroupGetDto fullStudentGroupGetDto = studentGroupService
                .convertToStudentGroupDto(fullFieldStudentGroup);

        StudentGroupPostDto fullStudentGroupPostDto = new StudentGroupPostDto();
        convertStudentGroupGetDtoToPostDto(fullStudentGroupGetDto, fullStudentGroupPostDto);

        StudentGroup anotherFullFieldStudentGroup = studentGroupService
                .convertToStudentGroup(fullStudentGroupPostDto);

        Assertions.assertEquals(fullFieldStudentGroup, anotherFullFieldStudentGroup);
        Assertions.assertEquals(studentList, anotherFullFieldStudentGroup.getStudents());
        Assertions.assertEquals(courseList, anotherFullFieldStudentGroup.getCourses());

    }

    private void convertStudentGroupGetDtoToPostDto(
            StudentGroupGetDto studentGroupGetDto, StudentGroupPostDto studentGroupPostDto) {

        String name = studentGroupGetDto.getName();
        studentGroupPostDto.setName(name);

        List<StudentGetDto> students = studentGroupGetDto.getStudents();
        if (students != null) {
            List<Long> studentIds = students.stream()
                    .map(StudentGetDto::getId)
                    .collect(Collectors.toList());

            studentGroupPostDto.setStudentIds(studentIds);
        }

        List<CourseGetDto> courses = studentGroupGetDto.getCourses();
        if (courses != null) {
            List<Long> courseIds = courses.stream()
                    .map(CourseGetDto::getId)
                    .collect(Collectors.toList());

            studentGroupPostDto.setCourseIds(courseIds);
        }

    }

}