package com.staffsterr2000.studentschedulerest.dataseed;

import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.entity.Student;
import com.staffsterr2000.studentschedulerest.model.service.CourseService;
import com.staffsterr2000.studentschedulerest.model.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final StudentService studentService;

    private final CourseService courseService;

    @Override
    public void run(String... args) throws Exception {
        Course course = new Course();
        course.setSubject(Course.Subject.HISTORY);
        course.setTeacher("Hans Renson");

        courseService.addCourse(course);

        Student student = new Student();
        student.setFirstName("Stan");
        student.setLastName("Rock");
        student.setCourses(List.of(course));

        studentService.addStudent(student);

    }

}
