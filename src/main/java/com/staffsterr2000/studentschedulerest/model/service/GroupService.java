package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.entity.StudentGroup;
import com.staffsterr2000.studentschedulerest.model.repo.GroupRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepo groupRepository;

    public List<StudentGroup> getGroups() {
        return groupRepository.findAll();
    }

    public void createGroup(StudentGroup studentGroup) {
        groupRepository.save(studentGroup);
    }
}
