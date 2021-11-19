package com.staffsterr2000.studentschedulerest.controller;

import com.staffsterr2000.studentschedulerest.dto.LectureDto;
import com.staffsterr2000.studentschedulerest.model.service.LectureService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lecture")
@AllArgsConstructor
public class LectureRestController {

    private final LectureService lectureService;

    @GetMapping("/{id}")
    @ResponseBody
    public LectureDto getLectureById(@PathVariable("id") Long lectureId) {
        return lectureService.getLectureById(lectureId);
    }

    @GetMapping
    @ResponseBody
    public List<LectureDto> getLectures() {
        return lectureService.getLectures();
    }

}
