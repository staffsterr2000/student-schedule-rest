package com.staffsterr2000.studentschedulerest.controller.rest;

import com.staffsterr2000.studentschedulerest.dto.get.AudienceGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.AudiencePostDto;
import com.staffsterr2000.studentschedulerest.entity.Audience;
import com.staffsterr2000.studentschedulerest.model.service.AudienceService;
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
@RequestMapping("/api/v1/audience")
@AllArgsConstructor
public class AudienceRestController {

    private final AudienceService audienceService;

    @GetMapping("/{id}")
    @ResponseBody
    public AudienceGetDto getAudienceById(@PathVariable("id") Long audienceId) {
        Audience audienceById = audienceService
                .getAudienceById(audienceId);
        return audienceService.convertToAudienceDto(audienceById);
    }

    @GetMapping
    @ResponseBody
    public List<AudienceGetDto> getAudiences() {
        return audienceService.getAudiences().stream()
                .map(audienceService::convertToAudienceDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Object> createAudience(
            @Valid @RequestBody AudiencePostDto audiencePostDto, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Audience audience = audienceService.convertToAudience(audiencePostDto);
        Audience createdAudience = audienceService.createAudience(audience);

        return new ResponseEntity<>(
                audienceService.convertToAudienceDto(createdAudience),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public void updateAudience(
            @PathVariable("id") Long audienceId,
            @RequestBody AudiencePostDto audiencePostDto) {

        Audience audience = audienceService.convertToAudience(audiencePostDto);
        audienceService.updateAudience(audienceId, audience);

    }


//    @PutMapping("/{id}")
//    public void updateAudience(
//            @PathVariable("id") Long audienceId,
//            @RequestParam(required = false, name = "room") Integer audienceRoomNumber) {
//
//        audienceService.updateAudience(audienceId, audience);
//    }

    @DeleteMapping("/{id}")
    public void deleteAudience(@PathVariable("id") Long audienceId) {
        audienceService.deleteAudience(audienceId);
    }

}
