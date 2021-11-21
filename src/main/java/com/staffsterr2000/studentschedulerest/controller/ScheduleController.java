package com.staffsterr2000.studentschedulerest.controller;

import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.LecturePostDto;
import com.staffsterr2000.studentschedulerest.dto.post.StudentPostDto;
import com.staffsterr2000.studentschedulerest.entity.Student;
import com.staffsterr2000.studentschedulerest.model.service.LectureService;
import com.staffsterr2000.studentschedulerest.model.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/schedule")
@AllArgsConstructor
public class ScheduleController {

    private final StudentService studentService;
    private final LectureService lectureService;


    // ???
    @GetMapping("/{id}")
    @ResponseBody
    public List<LectureGetDto> getStudentLectures(
            @PathVariable("id") Long id,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Student student = studentService.getStudentById(id);
        return studentService.getStudentScheduleByDate(student, date).stream()
                .map(lectureService::convertToLectureDto)
                .collect(Collectors.toList());
    }


}
