package com.staffsterr2000.studentschedulerest.controller.rest;

import com.staffsterr2000.studentschedulerest.dto.post.LecturePostDto;
import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
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
    public ResponseEntity<Object> createLecture(
            @Valid @RequestBody LecturePostDto lecture, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Long createdLectureId = lectureService.createLecture(lecture);
        return new ResponseEntity<>(
                String.format("Successfully created lecture with id %d", createdLectureId),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public void deleteLecture(@PathVariable("id") Long lectureId) {
        lectureService.deleteLecture(lectureId);
    }

}
