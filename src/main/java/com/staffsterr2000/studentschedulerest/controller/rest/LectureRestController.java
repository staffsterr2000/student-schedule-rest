package com.staffsterr2000.studentschedulerest.controller.rest;

import com.staffsterr2000.studentschedulerest.dto.LectureDto;
import com.staffsterr2000.studentschedulerest.dto.LectureGetDto;
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
    public LectureGetDto getLectureById(@PathVariable("id") Long lectureId) {
        return lectureService.getLectureById(lectureId);
    }

    @GetMapping
    @ResponseBody
    public List<LectureGetDto> getLectures() {
        return lectureService.getLectures();
    }

    @PostMapping
    public void createLecture(@RequestBody LectureDto lecture) {
        lectureService.createLecture(lecture);
    }

    @DeleteMapping("/{id}")
    public void deleteLecture(@PathVariable("id") Long lectureId) {
        lectureService.deleteLecture(lectureId);
    }

}
