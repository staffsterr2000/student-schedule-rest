package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.StudentGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.StudentPostDto;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.entity.Student;
import com.staffsterr2000.studentschedulerest.entity.StudentGroup;
import com.staffsterr2000.studentschedulerest.model.repo.StudentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepo studentRepository;

    private final StudentGroupService studentGroupService;

    private final ModelMapper modelMapper;

    @Autowired
    public StudentService(StudentRepo studentRepository,
                          @Lazy StudentGroupService studentGroupService,
                          ModelMapper modelMapper) {

        this.studentRepository = studentRepository;
        this.studentGroupService = studentGroupService;
        this.modelMapper = modelMapper;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("No such student with %d id.", studentId)));
    }

    @Transactional
    public Student createStudent(Student student) {
        Student savedStudent = studentRepository.save(student);

        StudentGroup studentGroup = savedStudent.getStudentGroup();
        if (studentGroup != null) {
            List<Student> students = studentGroup.getStudents();
            if (students == null)
                students = new ArrayList<>();

            students.add(savedStudent);
        }

        return savedStudent;
    }

    @Transactional
    public Student updateStudent(Long studentId, Student modifiedStudent) {
        Student studentFromDb = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Student with id %d doesn't exist", studentId)
                ));

        String modifiedFirstName = modifiedStudent.getFirstName();
        if (modifiedFirstName != null && !modifiedFirstName.isEmpty()) {
            studentFromDb.setFirstName(modifiedFirstName);
        }

        String modifiedLastName = modifiedStudent.getLastName();
        if (modifiedLastName != null && !modifiedLastName.isEmpty()) {
            studentFromDb.setLastName(modifiedLastName);
        }

        StudentGroup modifiedStudentGroup = modifiedStudent.getStudentGroup();
        if (modifiedStudentGroup != null) {
            studentFromDb.setStudentGroup(modifiedStudentGroup);

            List<Student> students = modifiedStudentGroup.getStudents();
            if (students == null)
                students = new ArrayList<>();

            if (!students.contains(studentFromDb))
                students.add(studentFromDb);
        }

        return studentRepository.save(studentFromDb);

    }

    @Transactional
    public void deleteStudent(Long studentId) {
        Student studentFromDb = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Student with id %d doesn't exist", studentId)
                ));

        StudentGroup studentGroup = studentFromDb.getStudentGroup();
        if (studentGroup != null) {
            List<Student> students = studentGroup.getStudents();
            students.remove(studentFromDb);
            studentFromDb.setStudentGroup(null);
        }

        studentRepository.deleteById(studentId);
    }

    public List<Lecture> getStudentScheduleByDate(Long studentId, LocalDate date) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Student with id %d doesn't exist", studentId)
                ));

        return student.getStudentGroup().getCourses().stream()
                .flatMap(course -> course.getLectures().stream())
                .filter(lecture -> lecture.getLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }

    public StudentGetDto convertToStudentDto(Student student) {
        return modelMapper.map(student, StudentGetDto.class);
    }

    public Student convertToStudent(StudentPostDto studentPostDto) {
        Student student = new Student();
        student.setFirstName(studentPostDto.getFirstName());
        student.setLastName(studentPostDto.getLastName());

        Long studentGroupId = studentPostDto.getStudentGroupId();
        if (studentGroupId != null) {
            StudentGroup studentGroup = studentGroupService
                    .getStudentGroupById(studentGroupId);

            student.setStudentGroup(studentGroup);
        }

        return student;
    }

}
