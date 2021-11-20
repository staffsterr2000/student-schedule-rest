package com.staffsterr2000.studentschedulerest.dataseed;

import com.staffsterr2000.studentschedulerest.dto.post.*;
import com.staffsterr2000.studentschedulerest.model.service.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
        AudiencePostDto audience112 = new AudiencePostDto();
        audience112.setRoomNumber(112);
        Long audience112Id = audienceService
                .createAudience(audience112);

        AudiencePostDto audience203 = new AudiencePostDto();
        audience203.setRoomNumber(203);
        Long audience203Id = audienceService
                .createAudience(audience203);

        AudiencePostDto audience309 = new AudiencePostDto();
        audience309.setRoomNumber(309);
        Long audience309Id = audienceService
                .createAudience(audience309);



        // courses
        CoursePostDto courseHistory = new CoursePostDto();
        courseHistory.setSubject(HISTORY);
        courseHistory.setTeacherFullName("Hans Renson");
        Long courseHistoryId = courseService.createCourse(courseHistory);

        CoursePostDto courseMath = new CoursePostDto();
        courseMath.setSubject(MATH);
        courseMath.setTeacherFullName("Another Teacher");
        Long courseMathId = courseService.createCourse(courseMath);

        CoursePostDto courseEnglish1 = new CoursePostDto();
        courseEnglish1.setSubject(ENGLISH);
        courseEnglish1.setTeacherFullName("Galina Ivanovna");
        Long courseEnglish1Id = courseService.createCourse(courseEnglish1);

        CoursePostDto courseEnglish2 = new CoursePostDto();
        courseEnglish2.setSubject(ENGLISH);
        courseEnglish2.setTeacherFullName("Jack Rachel");
        Long courseEnglish2Id = courseService.createCourse(courseEnglish2);




        // lectures
        LecturePostDto lecture1 = new LecturePostDto();
        lecture1.setCourseId(courseHistoryId);
        lecture1.setAudienceId(audience112Id);
        lecture1.setLocalDate(LocalDate.now().plusDays(1));
        Long lecture1Id = lectureService.createLecture(lecture1);

        LecturePostDto lecture2 = new LecturePostDto();
        lecture2.setCourseId(courseHistoryId);
        lecture2.setAudienceId(audience203Id);
        lecture2.setLocalDate(LocalDate.now().plusDays(2));
        Long lecture2Id = lectureService.createLecture(lecture2);

        LecturePostDto lecture3 = new LecturePostDto();
        lecture3.setCourseId(courseHistoryId);
        lecture3.setAudienceId(audience112Id);
        lecture3.setLocalDate(LocalDate.now().plusDays(3));
        Long lecture3Id = lectureService.createLecture(lecture3);

        LecturePostDto lecture4 = new LecturePostDto();
        lecture4.setCourseId(courseEnglish2Id);
        lecture4.setAudienceId(audience203Id);
        lecture4.setLocalDate(LocalDate.now().plusDays(1));
        Long lecture4Id = lectureService.createLecture(lecture4);

        LecturePostDto lecture5 = new LecturePostDto();
        lecture5.setCourseId(courseEnglish1Id);
        lecture5.setAudienceId(audience112Id);
        lecture5.setLocalDate(LocalDate.now().plusDays(2));
        Long lecture5Id = lectureService.createLecture(lecture5);

        LecturePostDto lecture6 = new LecturePostDto();
        lecture6.setCourseId(courseMathId);
        lecture6.setAudienceId(audience309Id);
        lecture6.setLocalDate(LocalDate.now().plusDays(2));
        Long lecture6Id = lectureService.createLecture(lecture6);

        LecturePostDto lecture7 = new LecturePostDto();
        lecture7.setCourseId(courseMathId);
        lecture7.setAudienceId(audience309Id);
        lecture7.setLocalDate(LocalDate.now().plusDays(3));
        Long lecture7Id = lectureService.createLecture(lecture7);

        LecturePostDto lecture8 = new LecturePostDto();
        lecture8.setCourseId(courseMathId);
        lecture8.setAudienceId(audience309Id);
        lecture8.setLocalDate(LocalDate.now().plusDays(3));
        Long lecture8Id = lectureService.createLecture(lecture8);

        LecturePostDto lecture9 = new LecturePostDto();
        lecture9.setCourseId(courseEnglish1Id);
        lecture9.setAudienceId(audience309Id);
        lecture9.setLocalDate(LocalDate.now().plusDays(1));
        Long lecture9Id = lectureService.createLecture(lecture9);



        // groups
        StudentGroupPostDto studentGroupKN17 = new StudentGroupPostDto();
        studentGroupKN17.setName("KN17");
        studentGroupKN17.addCourseId(courseHistoryId);
        studentGroupKN17.addCourseId(courseEnglish2Id);
        Long studentGroupKN17Id = studentGroupService
                .createStudentGroup(studentGroupKN17);

        StudentGroupPostDto studentGroupSA17 = new StudentGroupPostDto();
        studentGroupSA17.setName("SA17");
        studentGroupSA17.addCourseId(courseHistoryId);
        studentGroupSA17.addCourseId(courseMathId);
        Long studentGroupSA17Id = studentGroupService
                .createStudentGroup(studentGroupSA17);

        StudentGroupPostDto studentGroupAPP17 = new StudentGroupPostDto();
        studentGroupAPP17.setName("APP17");
        studentGroupAPP17.addCourseId(courseMathId);
        studentGroupAPP17.addCourseId(courseEnglish1Id);
        Long studentGroupAPP17Id = studentGroupService
                .createStudentGroup(studentGroupAPP17);



        // students
        StudentPostDto student1 = new StudentPostDto();
        student1.setFirstName("Stas");
        student1.setLastName("Rock");
        student1.setStudentGroupId(studentGroupKN17Id);
        studentService.createStudent(student1);

        StudentPostDto student2 = new StudentPostDto();
        student2.setFirstName("Roma");
        student2.setLastName("Kco");
        student2.setStudentGroupId(studentGroupKN17Id);
        studentService.createStudent(student2);

        StudentPostDto student3 = new StudentPostDto();
        student3.setFirstName("Katya");
        student3.setLastName("Kiwuk");
        student3.setStudentGroupId(studentGroupKN17Id);
        studentService.createStudent(student3);

        StudentPostDto student4 = new StudentPostDto();
        student4.setFirstName("Nastya");
        student4.setLastName("IsQueen");
        student4.setStudentGroupId(studentGroupSA17Id);
        studentService.createStudent(student4);

        StudentPostDto student5 = new StudentPostDto();
        student5.setFirstName("Sonya");
        student5.setLastName("Kombat");
        student5.setStudentGroupId(studentGroupAPP17Id);
        studentService.createStudent(student5);

        StudentPostDto student6 = new StudentPostDto();
        student6.setFirstName("Masha");
        student6.setLastName("Mishina");
        student6.setStudentGroupId(studentGroupAPP17Id);
        studentService.createStudent(student6);

    }

}
