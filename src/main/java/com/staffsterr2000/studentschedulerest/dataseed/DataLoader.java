package com.staffsterr2000.studentschedulerest.dataseed;

import com.staffsterr2000.studentschedulerest.entity.*;
import com.staffsterr2000.studentschedulerest.model.service.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.staffsterr2000.studentschedulerest.entity.Course.Subject.*;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final StudentService studentService;

    private final CourseService courseService;

    private final LectureService lectureService;

    private final StudentGroupService studentGroupService;

    private final AudienceService audienceService;

    @Override
    public void run(String... args) throws Exception {

        // audiences
        Audience audience112 = new Audience();
        audience112.setRoomNumber(112);
        audienceService.createAudience(audience112);

        Audience audience203 = new Audience();
        audience203.setRoomNumber(203);
        audienceService.createAudience(audience203);

        Audience audience309 = new Audience();
        audience309.setRoomNumber(309);
        audienceService.createAudience(audience309);



        // courses
        Course courseHistory = new Course();
        courseHistory.setSubject(HISTORY);
        courseHistory.setTeacherFullName("Hans Renson");
        courseService.createCourse(courseHistory);

        Course courseMath = new Course();
        courseMath.setSubject(MATH);
        courseMath.setTeacherFullName("Another Teacher");
        courseService.createCourse(courseMath);

        Course courseEnglish1 = new Course();
        courseEnglish1.setSubject(ENGLISH);
        courseEnglish1.setTeacherFullName("Galina Ivanovna");
        courseService.createCourse(courseEnglish1);

        Course courseEnglish2 = new Course();
        courseEnglish2.setSubject(ENGLISH);
        courseEnglish2.setTeacherFullName("Jack Rachel");
        courseService.createCourse(courseEnglish2);



        // lectures
        Lecture lecture1 = new Lecture();
        lecture1.setCourse(courseHistory);
        lecture1.setAudience(audience112);
        lecture1.setLocalDate(LocalDate.now().plusDays(1));
        lectureService.createLecture(lecture1);

        Lecture lecture2 = new Lecture();
        lecture2.setCourse(courseHistory);
        lecture2.setAudience(audience203);
        lecture2.setLocalDate(LocalDate.now().plusDays(2));
        lectureService.createLecture(lecture2);

        Lecture lecture3 = new Lecture();
        lecture3.setCourse(courseHistory);
        lecture3.setAudience(audience112);
        lecture3.setLocalDate(LocalDate.now().plusDays(3));
        lectureService.createLecture(lecture3);

        Lecture lecture4 = new Lecture();
        lecture4.setCourse(courseEnglish2);
        lecture4.setAudience(audience203);
        lecture4.setLocalDate(LocalDate.now().plusDays(1));
        lectureService.createLecture(lecture4);

        Lecture lecture5 = new Lecture();
        lecture5.setCourse(courseEnglish1);
        lecture5.setAudience(audience112);
        lecture5.setLocalDate(LocalDate.now().plusDays(2));
        lectureService.createLecture(lecture5);

        Lecture lecture6 = new Lecture();
        lecture6.setCourse(courseMath);
        lecture6.setAudience(audience309);
        lecture6.setLocalDate(LocalDate.now().plusDays(2));
        lectureService.createLecture(lecture6);

        Lecture lecture7 = new Lecture();
        lecture7.setCourse(courseMath);
        lecture7.setAudience(audience309);
        lecture7.setLocalDate(LocalDate.now().plusDays(3));
        lectureService.createLecture(lecture7);

        Lecture lecture8 = new Lecture();
        lecture8.setCourse(courseMath);
        lecture8.setAudience(audience309);
        lecture8.setLocalDate(LocalDate.now().plusDays(3));
        lectureService.createLecture(lecture8);

        Lecture lecture9 = new Lecture();
        lecture9.setCourse(courseEnglish1);
        lecture9.setAudience(audience309);
        lecture9.setLocalDate(LocalDate.now().plusDays(1));
        lectureService.createLecture(lecture9);



        // groups
        StudentGroup studentGroupKN17 = new StudentGroup();
        studentGroupKN17.setName("KN17");
        studentGroupKN17.setCourses(List.of(courseHistory, courseEnglish2));
        studentGroupService.createGroup(studentGroupKN17);

        StudentGroup studentGroupSA17 = new StudentGroup();
        studentGroupSA17.setName("SA17");
        studentGroupSA17.setCourses(List.of(courseHistory, courseMath));
        studentGroupService.createGroup(studentGroupSA17);

        StudentGroup studentGroupAPP17 = new StudentGroup();
        studentGroupAPP17.setName("APP17");
        studentGroupAPP17.setCourses(List.of(courseMath, courseEnglish1));
        studentGroupService.createGroup(studentGroupAPP17);



        // students
        Student student1 = new Student();
        student1.setFirstName("Stas");
        student1.setLastName("Rock");
        student1.setStudentGroup(studentGroupKN17);
        studentService.createStudent(student1);

        Student student2 = new Student();
        student2.setFirstName("Roma");
        student2.setLastName("Kco");
        student2.setStudentGroup(studentGroupKN17);
        studentService.createStudent(student2);

        Student student3 = new Student();
        student3.setFirstName("Katya");
        student3.setLastName("Kiwuk");
        student3.setStudentGroup(studentGroupKN17);
        studentService.createStudent(student3);

        Student student4 = new Student();
        student4.setFirstName("Nastya");
        student4.setLastName("IsQueen");
        student4.setStudentGroup(studentGroupSA17);
        studentService.createStudent(student4);

        Student student5 = new Student();
        student5.setFirstName("Sonya");
        student5.setLastName("Kombat");
        student5.setStudentGroup(studentGroupAPP17);
        studentService.createStudent(student5);

        Student student6 = new Student();
        student6.setFirstName("Masha");
        student6.setLastName("Mishina");
        student6.setStudentGroup(studentGroupAPP17);
        studentService.createStudent(student6);

    }

}
