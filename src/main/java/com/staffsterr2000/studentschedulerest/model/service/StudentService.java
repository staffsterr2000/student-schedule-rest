package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGroupGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.StudentPostDto;
import com.staffsterr2000.studentschedulerest.entity.Student;
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

    public List<StudentGetDto> getStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToStudentDto)
                .collect(Collectors.toList());
    }

    public StudentGetDto getStudentById(Long studentId) {
        Student studentById = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(String.format("No such student with %d id.", studentId)));
        return convertToStudentDto(studentById);
    }

    @Transactional
    public Long createStudent(StudentPostDto studentPostDto) {
        Student student = convertToStudent(studentPostDto);
        return studentRepository.save(student).getId();
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

    public List<LectureGetDto> getStudentScheduleByDate(StudentGetDto student, LocalDate date) {
        return student.getStudentGroup().getCourses().stream()
                .flatMap(course -> course.getLectures().stream())
                .filter(lecture -> lecture.getLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }

    private StudentGetDto convertToStudentDto(Student student) {
        return modelMapper.map(student, StudentGetDto.class);
    }

    private Student convertToStudent(StudentPostDto studentPostDto) {
        StudentGetDto studentGetDto =
                transformAndFetchAllStudentDataToDto(studentPostDto);

        return modelMapper.map(studentGetDto, Student.class);
    }

    private StudentGetDto transformAndFetchAllStudentDataToDto(StudentPostDto studentPostDto) {
        StudentGetDto studentGetDto = new StudentGetDto();

        studentGetDto.setFirstName(studentPostDto.getFirstName());
        studentGetDto.setLastName(studentPostDto.getLastName());

        Long studentGroupId = studentPostDto.getStudentGroupId();
        if (studentGroupId != null) {
            StudentGroupGetDto studentGroupGetDto = studentGroupService
                    .getStudentGroupById(studentGroupId);
            studentGetDto.setStudentGroup(studentGroupGetDto);
//            studentGroupGetDto.getStudents().add(studentGetDto);
        }

        return studentGetDto;
    }
}
