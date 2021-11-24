package com.staffsterr2000.studentschedulerest.controller.rest;

import com.staffsterr2000.studentschedulerest.dto.post.LecturePostDto;
import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.model.service.LectureService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lecture")
@AllArgsConstructor
public class LectureRestController {

    private final LectureService lectureService;

    @GetMapping("/{id}")
    @ResponseBody
    public LectureGetDto getLectureById(@PathVariable("id") Long lectureId) {
        Lecture lectureById = lectureService.getLectureById(lectureId);
        return lectureService.convertToLectureDto(lectureById);
    }

    @GetMapping
    @ResponseBody
    public List<LectureGetDto> getLectures() {
        return lectureService.getLectures().stream()
                .map(lectureService::convertToLectureDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Object> createLecture(
            @Valid @RequestBody LecturePostDto lecturePostDto, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Lecture lecture = lectureService.convertToLecture(lecturePostDto);
        Lecture createdLecture= lectureService.createLecture(lecture);
        return new ResponseEntity<>(
                lectureService.convertToLectureDto(createdLecture),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<LectureGetDto> updateLecture(
            @PathVariable("id") Long lectureId,
            @RequestBody LecturePostDto lecturePostDto) {

        Lecture lecture = lectureService.convertToLecture(lecturePostDto);
        Lecture updatedLecture = lectureService.updateLecture(lectureId, lecture);

        return new ResponseEntity<>(
                lectureService.convertToLectureDto(updatedLecture),
                HttpStatus.OK
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLecture(
            @PathVariable("id") Long lectureId) {

        lectureService.deleteLecture(lectureId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
