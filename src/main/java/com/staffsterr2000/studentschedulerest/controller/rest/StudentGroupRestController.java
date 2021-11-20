package com.staffsterr2000.studentschedulerest.controller.rest;

import com.staffsterr2000.studentschedulerest.dto.StudentGroupDto;
import com.staffsterr2000.studentschedulerest.model.service.StudentGroupService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sgroup")
@AllArgsConstructor
public class StudentGroupRestController {

    private final StudentGroupService studentGroupService;

    @GetMapping("/{id}")
    @ResponseBody
    public StudentGroupDto getStudentGroupById(@PathVariable("id") Long studentGroupId) {
        return studentGroupService.getStudentGroupById(studentGroupId);
    }

    @GetMapping
    @ResponseBody
    public List<StudentGroupDto> getStudentGroups() {
        return studentGroupService.getStudentGroups();
    }

    @PostMapping
    public void createStudentGroup(@RequestBody StudentGroupDto studentGroup) {
        studentGroupService.createGroup(studentGroup);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentGroup(@PathVariable("id") Long studentGroupId) {
        studentGroupService.deleteStudentGroup(studentGroupId);
    }

}
