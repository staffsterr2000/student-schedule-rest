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

        // course 1
        Course course1 = new Course();
        course1.setSubject(Course.Subject.HISTORY);
        course1.setTeacherFullName("Hans Renson");
        courseService.addCourse(course1);

        // course 2
        Course course2 = new Course();
        course2.setSubject(Course.Subject.MATH);
        course2.setTeacherFullName("Another Teacher");
        courseService.addCourse(course2);



        // lecture 1
        Lecture lecture1 = new Lecture();
        lecture1.setCourse(course1);
        lecture1.setLocalDate(LocalDate.now().plusDays(1));
        lectureService.addLecture(lecture1);

        // lecture 2
        Lecture lecture2 = new Lecture();
        lecture2.setCourse(course2);
        lecture2.setLocalDate(LocalDate.now().plusDays(2));
        lectureService.addLecture(lecture2);

        Lecture lecture3 = new Lecture();
        lecture3.setCourse(course1);
        lecture3.setLocalDate(LocalDate.now().plusDays(2));
        lectureService.addLecture(lecture3);



        // student 1
        Student student1 = new Student();
        student1.setFirstName("Stan");
        student1.setLastName("Rock");
        student1.setCourses(List.of(course1));
        studentService.addStudent(student1);

        // student 2
        Student student2 = new Student();
        student2.setFirstName("Nats");
        student2.setLastName("Kco");
        student2.setCourses(List.of(course1, course2));
        studentService.addStudent(student2);

    }

}
