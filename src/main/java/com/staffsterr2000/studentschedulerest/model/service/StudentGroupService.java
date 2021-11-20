package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.CourseGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGroupGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.StudentGroupPostDto;
import com.staffsterr2000.studentschedulerest.entity.StudentGroup;
import com.staffsterr2000.studentschedulerest.model.repo.StudentGroupRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentGroupService {

    private final StudentGroupRepo studentGroupRepository;
    private final StudentService studentService;
    private final CourseService courseService;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentGroupService(StudentGroupRepo studentGroupRepository,
                               @Lazy StudentService studentService,
                               @Lazy CourseService courseService,
                               ModelMapper modelMapper) {

        this.studentGroupRepository = studentGroupRepository;
        this.studentService = studentService;
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    public StudentGroupGetDto getStudentGroupById(Long studentGroupId) {
        StudentGroup studentGroupById = studentGroupRepository.findById(studentGroupId).
                orElseThrow(() -> new IllegalStateException(
                        String.format("No such student group with %d id.", studentGroupId)));
        return convertToStudentGroupDto(studentGroupById);
    }

    public List<StudentGroupGetDto> getStudentGroups() {
        return studentGroupRepository.findAll().stream()
                .map(this::convertToStudentGroupDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createStudentGroup(StudentGroupPostDto studentGroupPostDto) {
        String groupName = studentGroupPostDto.getName();
        boolean groupNameAlreadyTaken = studentGroupRepository
                .findByName(groupName).isPresent();

        if (groupNameAlreadyTaken) {
            throw new IllegalStateException(
                    String.format("Group name \"%s\" has already been taken.", groupName));
        }

        StudentGroup studentGroup = convertToStudentGroup(studentGroupPostDto);
        return studentGroupRepository.save(studentGroup).getId();
    }

    @Transactional
    public void deleteStudentGroup(Long studentGroupId) {
        boolean studentGroupExists =
                studentGroupRepository.existsById(studentGroupId);

        if (!studentGroupExists) {
            throw new IllegalStateException(
                    String.format("Student group with id %d doesn't exist", studentGroupId));
        }

        studentGroupRepository.deleteById(studentGroupId);
    }

    private StudentGroupGetDto convertToStudentGroupDto(StudentGroup studentGroup) {
        return modelMapper.map(studentGroup, StudentGroupGetDto.class);
    }

    private StudentGroup convertToStudentGroup(StudentGroupPostDto studentGroupPostDto) {
        StudentGroupGetDto studentGroupGetDto =
                transformAndFetchAllStudentGroupDataToDto(studentGroupPostDto);

        return modelMapper.map(studentGroupGetDto, StudentGroup.class);
    }

    private StudentGroupGetDto transformAndFetchAllStudentGroupDataToDto(
            StudentGroupPostDto studentGroupPostDto) {

        StudentGroupGetDto studentGroupGetDto = new StudentGroupGetDto();
        studentGroupGetDto.setName(studentGroupPostDto.getName());

        List<Long> studentIds = studentGroupPostDto.getStudentIds();
        if (studentIds != null) {
            List<StudentGetDto> studentGetDtos = studentIds.stream()
                    .map(studentService::getStudentById)
                    .collect(Collectors.toList());

            studentGroupGetDto.setStudents(studentGetDtos);

            studentGetDtos.forEach(studentGetDto ->
                    studentGetDto.setStudentGroup(studentGroupGetDto));
        }

        List<Long> courseIds = studentGroupPostDto.getCourseIds();
        if (courseIds != null) {
            List<CourseGetDto> courseGetDtos = courseIds.stream()
                    .map(courseService::getCourseById)
                    .collect(Collectors.toList());

            studentGroupGetDto.setCourses(courseGetDtos);

            courseGetDtos.stream()
                    .map(CourseGetDto::getStudentGroups)
                    .forEach(list -> list.add(studentGroupGetDto));
        }

        return studentGroupGetDto;
    }

}
