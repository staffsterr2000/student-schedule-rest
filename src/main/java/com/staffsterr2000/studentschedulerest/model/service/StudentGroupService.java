package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.CourseGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.StudentGroupGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.StudentGroupPostDto;
import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.entity.Student;
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

    public StudentGroup getStudentGroupById(Long studentGroupId) {
        return studentGroupRepository.findById(studentGroupId).
                orElseThrow(() -> new IllegalStateException(
                        String.format("No such student group with %d id.", studentGroupId)));
    }

    public List<StudentGroup> getStudentGroups() {
        return studentGroupRepository.findAll();
    }

    @Transactional
    public StudentGroup createStudentGroup(StudentGroup studentGroup) {
        String studentGroupName = studentGroup.getName();
        boolean groupNameAlreadyTaken = studentGroupRepository
                .findByName(studentGroupName).isPresent();

        if (groupNameAlreadyTaken) {
            throw new IllegalStateException(
                    String.format("Group name \"%s\" has already been taken.", studentGroupName));
        }

        return studentGroupRepository.save(studentGroup);
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

    public StudentGroupGetDto convertToStudentGroupDto(StudentGroup studentGroup) {
        return modelMapper.map(studentGroup, StudentGroupGetDto.class);
    }

    public StudentGroup convertToStudentGroup(StudentGroupPostDto studentGroupPostDto) {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setName(studentGroupPostDto.getName());

        List<Long> studentIds = studentGroupPostDto.getStudentIds();
        if (studentIds != null) {
            List<Student> students = studentIds.stream()
                    .map(studentService::getStudentById)
                    .collect(Collectors.toList());

            studentGroup.setStudents(students);

//            students.forEach(student ->
//                    student.setStudentGroup(studentGroup));
        }

        List<Long> courseIds = studentGroupPostDto.getCourseIds();
        if (courseIds != null) {
            List<Course> courses = courseIds.stream()
                    .map(courseService::getCourseById)
                    .collect(Collectors.toList());

            studentGroup.setCourses(courses);

//            courses.stream()
//                    .map(Course::getStudentGroups)
//                    .forEach(list -> list.add(studentGroup));
        }

        return studentGroup;

    }

}
