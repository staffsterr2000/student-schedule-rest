package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGroupGetDto;
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
                .orElseThrow(() -> new IllegalStateException(String.format("No such student with %d id.", studentId)));
    }

    @Transactional
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }


//    @SuppressWarnings("UnusedReturnValue")
//    public boolean updateStudent(Long studentId, Student modifiedStudent) {
//        boolean audienceIsPresent =
//                studentRepository.existsById(studentId);
//
//        if (audienceIsPresent) {
//            audience.setId(studentId);
//            audienceRepository.save(audience);
//        }
//
//        return audienceIsPresent;
//    }

    @Transactional
    public void deleteStudent(Long studentId) {
        boolean studentExists =
                studentRepository.existsById(studentId);

        if (!studentExists) {
            throw new IllegalStateException(String.format("Student with id %d doesn't exist", studentId));
        }

        studentRepository.deleteById(studentId);
    }

    public List<Lecture> getStudentScheduleByDate(Student student, LocalDate date) {
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
//            studentGroupGetDto.getStudents().add(studentGetDto);
        }

        return student;
    }

}
