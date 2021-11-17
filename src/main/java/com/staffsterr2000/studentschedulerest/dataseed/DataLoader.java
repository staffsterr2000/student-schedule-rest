package com.staffsterr2000.studentschedulerest.dataseed;

import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.entity.Student;
import com.staffsterr2000.studentschedulerest.model.service.CourseService;
import com.staffsterr2000.studentschedulerest.model.service.LectureService;
import com.staffsterr2000.studentschedulerest.model.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final StudentService studentService;

    private final CourseService courseService;

    private final LectureService lectureService;

    @Override
    public void run(String... args) throws Exception {
        // lecture 1
        Lecture lecture1 = new Lecture();
        lecture1.setLocalDate(LocalDate.now().plusDays(1));
        lectureService.addLecture(lecture1);

        // lecture 2
        Lecture lecture2 = new Lecture();
        lecture2.setLocalDate(LocalDate.now().plusDays(2));
        lectureService.addLecture(lecture2);

        // course
        Course course = new Course();
        course.setSubject(Course.Subject.HISTORY);
        course.setTeacher("Hans Renson");
        course.setLectures(List.of(lecture1, lecture2));
        courseService.addCourse(course);

        // student
        Student student = new Student();
        student.setFirstName("Stan");
        student.setLastName("Rock");
        student.setCourses(List.of(course));
        studentService.addStudent(student);

    }

}
