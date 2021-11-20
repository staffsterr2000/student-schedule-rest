package com.staffsterr2000.studentschedulerest.controller;

import com.staffsterr2000.studentschedulerest.dto.LectureDto;
import com.staffsterr2000.studentschedulerest.dto.StudentDto;
import com.staffsterr2000.studentschedulerest.model.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule")
@AllArgsConstructor
public class ScheduleController {

    private final StudentService studentService;


    @GetMapping("/{id}")
    @ResponseBody
    public List<LectureDto> getStudentLectures(
            @PathVariable("id") Long id,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        StudentDto student = studentService.getStudentById(id);
        return studentService.getStudentScheduleByDate(student, date);
    }


}
