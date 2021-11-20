package com.staffsterr2000.studentschedulerest.controller.rest;

import com.staffsterr2000.studentschedulerest.dto.AudienceDto;
import com.staffsterr2000.studentschedulerest.model.service.AudienceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audience")
@AllArgsConstructor
public class AudienceRestController {

    private final AudienceService audienceService;

    @GetMapping("/{id}")
    @ResponseBody
    public AudienceDto getAudienceById(@PathVariable("id") Long audienceId) {
        return audienceService.getAudienceById(audienceId);
    }

    @GetMapping
    @ResponseBody
    public List<AudienceDto> getAudiences() {
        return audienceService.getAudiences();
    }

    @PostMapping
    public void createAudience(@RequestBody AudienceDto audience) {
        audienceService.createAudience(audience);
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
