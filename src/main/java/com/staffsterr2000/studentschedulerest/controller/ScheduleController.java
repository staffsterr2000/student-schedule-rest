package com.staffsterr2000.studentschedulerest.controller;

import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
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

    @GetMapping("/{id}")
    @ResponseBody
    public List<LectureGetDto> getStudentLectures(
            @PathVariable("id") Long id,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<Lecture> lectures = studentService
                .getStudentScheduleByDate(id, date);

        return convertLecturesToLectureDtos(lectures);
    }

    private List<LectureGetDto> convertLecturesToLectureDtos(List<Lecture> lectures) {
        return lectures.stream()
                .map(lectureService::convertToLectureDto)
                .collect(Collectors.toList());
    }

}
