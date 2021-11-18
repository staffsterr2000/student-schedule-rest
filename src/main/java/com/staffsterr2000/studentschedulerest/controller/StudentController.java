package com.staffsterr2000.studentschedulerest.controller;

import com.staffsterr2000.studentschedulerest.dto.LectureDto;
import com.staffsterr2000.studentschedulerest.dto.StudentDto;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.entity.Student;
import com.staffsterr2000.studentschedulerest.model.service.StudentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/student")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final ModelMapper modelMapper;


    @GetMapping("/{id}")
    @ResponseBody
    public List<LectureDto> getStudentLectures(
            @PathVariable("id")
                    Long id,

            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate date) {

        Student student = studentService.getStudent(id);
        List<Lecture> lectures = studentService
                .getStudentLecturesByDate(student, date);

        return lectures.stream()
                .map(this::convertToLectureDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    @ResponseBody
    public List<StudentDto> getStudents() {
        return studentService.getStudents().stream()
                .map(this::convertToStudentDto)
                .collect(Collectors.toList());
    }

    private LectureDto convertToLectureDto(Lecture lecture) {
        return modelMapper.map(lecture, LectureDto.class);
    }

    private StudentDto convertToStudentDto(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

}
