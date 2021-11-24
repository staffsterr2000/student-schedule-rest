package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.StudentGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGroupGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.StudentPostDto;
import com.staffsterr2000.studentschedulerest.entity.Student;
import com.staffsterr2000.studentschedulerest.entity.StudentGroup;
import com.staffsterr2000.studentschedulerest.model.repo.StudentRepo;
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
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private StudentRepo studentRepository;

    @MockBean
    private StudentGroupService studentGroupService;

    @Test
    void shouldThrowExceptionIfGivenIdDoesNotExist() {
        Student student = new Student();

        Long existingId = 1L;
        Long fakeId = 2L;

        Mockito.doReturn(Optional.of(student))
                .when(studentRepository)
                .findById(existingId);

        // getStudentById method
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentService.getStudentById(null));
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentService.getStudentById(fakeId));

        // updateStudent method
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentService.updateStudent(null, student));
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentService.updateStudent(fakeId, student));

        // deleteStudent method
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentService.deleteStudent(null));
        Assertions.assertThrows(IllegalStateException.class,
                () -> studentService.deleteStudent(fakeId));

    }

    @Test
    void shouldSetInverseDependenciesDuringCreating() {
        Student student = new Student();

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(1L);
        studentGroup.setName("KIW19-1");
        studentGroup.setStudents(new ArrayList<>());
        student.setStudentGroup(studentGroup);

        Mockito.doReturn(student)
                .when(studentRepository)
                .save(student);

        studentService.createStudent(student);

        Assertions.assertTrue(studentGroup.getStudents().stream()
                .anyMatch(student::equals));

    }

    @Test
    void shouldSetDirectAndInverseDependenciesDuringUpdating() {
        Student student = new Student();
        student.setFirstName("Abdul");
        student.setLastName("Ibn");

        Long id = 1L;

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(1L);
        studentGroup.setName("KIW19-1");
        studentGroup.setStudents(new ArrayList<>());
        student.setStudentGroup(studentGroup);

        Student emptyStudent = new Student();
        Mockito.doReturn(Optional.of(emptyStudent))
                .when(studentRepository)
                .findById(id);

        studentService.updateStudent(id, student);

        // direct
        Assertions.assertEquals(student, emptyStudent);

        // inverse
        Assertions.assertTrue(studentGroup.getStudents().stream()
                .anyMatch(student::equals));

    }

    // TODO: implement working with schedule unit test logic
    @Test
    void shouldGiveScheduleBackCorrectly() {

    }

    // TODO: implement deletion test logic
    @Test
    void shouldSuccessfullyDeleteEntityFromDb() {

    }

    @Test
    void shouldCorrectlyConvertEntityToDtoAndBackwards() {
        Student emptyFieldStudent = new Student();

        StudentGetDto emptyStudentGetDto = studentService
                .convertToStudentDto(emptyFieldStudent);

        StudentPostDto emptyStudentPostDto = new StudentPostDto();
        convertStudentGetDtoToPostDto(emptyStudentGetDto, emptyStudentPostDto);

        Student anotherEmptyFieldStudent = studentService
                .convertToStudent(emptyStudentPostDto);

        Assertions.assertEquals(emptyFieldStudent, anotherEmptyFieldStudent);

        // ---------------------------------------------------------------

        Student fullFieldStudent = new Student();
        fullFieldStudent.setId(1L);
        fullFieldStudent.setFirstName("Abdul");
        fullFieldStudent.setLastName("Ibn");

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(1L);
        studentGroup.setName("KIW19-1");
        fullFieldStudent.setStudentGroup(studentGroup);

        Mockito.doReturn(studentGroup)
                .when(studentGroupService)
                .getStudentGroupById(studentGroup.getId());

        StudentGetDto fullStudentGetDto = studentService
                .convertToStudentDto(fullFieldStudent);

        StudentPostDto fullStudentPostDto = new StudentPostDto();
        convertStudentGetDtoToPostDto(fullStudentGetDto, fullStudentPostDto);

        Student anotherFullFieldStudent = studentService
                .convertToStudent(fullStudentPostDto);

        Assertions.assertEquals(fullFieldStudent, anotherFullFieldStudent);
        Assertions.assertEquals(studentGroup, anotherFullFieldStudent.getStudentGroup());

    }

    private void convertStudentGetDtoToPostDto(
            StudentGetDto studentGetDto, StudentPostDto studentPostDto) {

        String firstName = studentGetDto.getFirstName();
        studentPostDto.setFirstName(firstName);

        String lastName = studentGetDto.getLastName();
        studentPostDto.setLastName(lastName);

        StudentGroupGetDto studentGroup = studentGetDto.getStudentGroup();
        if (studentGroup != null) {
            studentPostDto.setStudentGroupId(studentGroup.getId());
        }

    }

}