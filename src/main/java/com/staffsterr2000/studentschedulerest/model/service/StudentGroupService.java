package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.StudentGroupDto;
import com.staffsterr2000.studentschedulerest.entity.StudentGroup;
import com.staffsterr2000.studentschedulerest.model.repo.StudentGroupRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentGroupService {

    private final StudentGroupRepo studentGroupRepository;
    private final ModelMapper modelMapper;

    public StudentGroupDto getStudentGroupById(Long studentGroupId) {
        StudentGroup studentGroupById = studentGroupRepository.findById(studentGroupId).
                orElseThrow(() -> new IllegalStateException(String.format("No such student group with %d id.", studentGroupId)));
        return convertToStudentGroupDto(studentGroupById);
    }
    public List<StudentGroupDto> getStudentGroups() {
        return studentGroupRepository.findAll().stream()
                .map(this::convertToStudentGroupDto)
                .collect(Collectors.toList());
    }

    // raw create
    @Transactional
    public void createGroup(StudentGroupDto studentGroupDto) {
        StudentGroup studentGroup = convertToStudentGroup(studentGroupDto);
        studentGroupRepository.save(studentGroup);
    }

    @Transactional
    public void deleteStudentGroup(Long studentGroupId) {
        boolean studentGroupExists =
                studentGroupRepository.existsById(studentGroupId);

        if (!studentGroupExists) {
            throw new IllegalStateException(String.format("Student group with id %d doesn't exist", studentGroupId));
        }

        studentGroupRepository.deleteById(studentGroupId);
    }

    private StudentGroupDto convertToStudentGroupDto(StudentGroup studentGroup) {
        return modelMapper.map(studentGroup, StudentGroupDto.class);
    }

    private StudentGroup convertToStudentGroup(StudentGroupDto studentGroupDto) {
        return modelMapper.map(studentGroupDto, StudentGroup.class);
    }

}
