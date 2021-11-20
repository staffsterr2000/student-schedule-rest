package com.staffsterr2000.studentschedulerest.dataseed;

import com.staffsterr2000.studentschedulerest.dto.*;
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
        AudienceDto audience112 = new AudienceDto();
        audience112.setRoomNumber(112);
        Long audience112Id = audienceService
                .createAudience(audience112);

        AudienceDto audience203 = new AudienceDto();
        audience203.setRoomNumber(203);
        Long audience203Id = audienceService
                .createAudience(audience203);

        AudienceDto audience309 = new AudienceDto();
        audience309.setRoomNumber(309);
        Long audience309Id = audienceService
                .createAudience(audience309);



        // courses
        CourseDto courseHistory = new CourseDto();
        courseHistory.setSubject(HISTORY);
        courseHistory.setTeacherFullName("Hans Renson");

        CourseDto courseMath = new CourseDto();
        courseMath.setSubject(MATH);
        courseMath.setTeacherFullName("Another Teacher");

        CourseDto courseEnglish1 = new CourseDto();
        courseEnglish1.setSubject(ENGLISH);
        courseEnglish1.setTeacherFullName("Galina Ivanovna");

        CourseDto courseEnglish2 = new CourseDto();
        courseEnglish2.setSubject(ENGLISH);
        courseEnglish2.setTeacherFullName("Jack Rachel");



        // lectures
        LectureDto lecture1 = new LectureDto();
//        lecture1.setCourseId(courseHistory);
        lecture1.setAudienceId(audience112Id);
        lecture1.setLocalDate(LocalDate.now().plusDays(1));

        LectureDto lecture2 = new LectureDto();
//        lecture2.setCourse(courseHistory);
//        lecture2.setAudience(audience203);
        lecture2.setLocalDate(LocalDate.now().plusDays(2));

        LectureDto lecture3 = new LectureDto();
//        lecture3.setCourse(courseHistory);
//        lecture3.setAudience(audience112);
        lecture3.setLocalDate(LocalDate.now().plusDays(3));

        LectureDto lecture4 = new LectureDto();
//        lecture4.setCourse(courseEnglish2);
//        lecture4.setAudience(audience203);
        lecture4.setLocalDate(LocalDate.now().plusDays(1));

        LectureDto lecture5 = new LectureDto();
//        lecture5.setCourse(courseEnglish1);
//        lecture5.setAudience(audience112);
        lecture5.setLocalDate(LocalDate.now().plusDays(2));

        LectureDto lecture6 = new LectureDto();
//        lecture6.setCourse(courseMath);
//        lecture6.setAudience(audience309);
        lecture6.setLocalDate(LocalDate.now().plusDays(2));

        LectureDto lecture7 = new LectureDto();
//        lecture7.setCourse(courseMath);
//        lecture7.setAudience(audience309);
        lecture7.setLocalDate(LocalDate.now().plusDays(3));

        LectureDto lecture8 = new LectureDto();
//        lecture8.setCourse(courseMath);
//        lecture8.setAudience(audience309);
        lecture8.setLocalDate(LocalDate.now().plusDays(3));

        LectureDto lecture9 = new LectureDto();
//        lecture9.setCourse(courseEnglish1);
//        lecture9.setAudience(audience309);
        lecture9.setLocalDate(LocalDate.now().plusDays(1));



        // groups
        StudentGroupDto studentGroupKN17 = new StudentGroupDto();
        studentGroupKN17.setName("KN17");
//        studentGroupKN17.addCourse(courseHistory);
//        studentGroupKN17.addCourse(courseEnglish2);

        StudentGroupDto studentGroupSA17 = new StudentGroupDto();
        studentGroupSA17.setName("SA17");
//        studentGroupSA17.addCourse(courseHistory);
//        studentGroupSA17.addCourse(courseMath);

        StudentGroupDto studentGroupAPP17 = new StudentGroupDto();
        studentGroupAPP17.setName("APP17");
//        studentGroupAPP17.addCourse(courseMath);
//        studentGroupAPP17.addCourse(courseEnglish1);



        // students
        StudentDto student1 = new StudentDto();
        student1.setFirstName("Stas");
        student1.setLastName("Rock");
//        student1.setStudentGroup(studentGroupKN17);

        StudentDto student2 = new StudentDto();
        student2.setFirstName("Roma");
        student2.setLastName("Kco");
//        student2.setStudentGroup(studentGroupKN17);

        StudentDto student3 = new StudentDto();
        student3.setFirstName("Katya");
        student3.setLastName("Kiwuk");
//        student3.setStudentGroup(studentGroupKN17);

        StudentDto student4 = new StudentDto();
        student4.setFirstName("Nastya");
        student4.setLastName("IsQueen");
//        student4.setStudentGroup(studentGroupSA17);

        StudentDto student5 = new StudentDto();
        student5.setFirstName("Sonya");
        student5.setLastName("Kombat");
//        student5.setStudentGroup(studentGroupAPP17);

        StudentDto student6 = new StudentDto();
        student6.setFirstName("Masha");
        student6.setLastName("Mishina");
//        student6.setStudentGroup(studentGroupAPP17);



        courseService.createCourse(courseHistory);
        courseService.createCourse(courseMath);
        courseService.createCourse(courseEnglish1);
        courseService.createCourse(courseEnglish2);

        lectureService.createLecture(lecture1);
        lectureService.createLecture(lecture2);
        lectureService.createLecture(lecture3);
        lectureService.createLecture(lecture4);
        lectureService.createLecture(lecture5);
        lectureService.createLecture(lecture6);
        lectureService.createLecture(lecture7);
        lectureService.createLecture(lecture8);
        lectureService.createLecture(lecture9);

        studentGroupService.createGroup(studentGroupKN17);
        studentGroupService.createGroup(studentGroupSA17);
        studentGroupService.createGroup(studentGroupAPP17);

        studentService.createStudent(student1);
        studentService.createStudent(student2);
        studentService.createStudent(student3);
        studentService.createStudent(student4);
        studentService.createStudent(student5);
        studentService.createStudent(student6);
    }

}
