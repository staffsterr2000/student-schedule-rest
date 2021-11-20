package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.LectureDto;
import com.staffsterr2000.studentschedulerest.dto.StudentDto;
import com.staffsterr2000.studentschedulerest.entity.Student;
import com.staffsterr2000.studentschedulerest.model.repo.StudentRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepo studentRepository;
    private final CourseService courseService;
    private final ModelMapper modelMapper;


    public List<StudentDto> getStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToStudentDto)
                .collect(Collectors.toList());
    }

    public StudentDto getStudentById(Long studentId) {
        Student studentById = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(String.format("No such student with %d id.", studentId)));
        return convertToStudentDto(studentById);
    }

    // raw
    @Transactional
    public void createStudent(StudentDto studentDto) {
        Student student = convertToStudent(studentDto);
        studentRepository.save(student);
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

    public List<LectureDto> getStudentScheduleByDate(StudentDto student, LocalDate date) {
        return student.getStudentGroup().getCourses().stream()
                .flatMap(course -> course.getLectures().stream())
                .filter(lecture -> lecture.getLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }

    private StudentDto convertToStudentDto(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    private Student convertToStudent(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }
}
