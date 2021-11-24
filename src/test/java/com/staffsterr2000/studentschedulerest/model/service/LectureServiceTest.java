package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.AudienceGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.CourseGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.LecturePostDto;
import com.staffsterr2000.studentschedulerest.entity.Audience;
import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.model.repo.LectureRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class LectureServiceTest {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private LectureRepo lectureRepository;

    @MockBean
    private AudienceService audienceService;

    @MockBean
    private CourseService courseService;

    @Test
    void shouldThrowExceptionIfGivenIdDoesNotExist() {
        Lecture lecture = new Lecture();

        Long existingId = 1L;
        Long fakeId = 2L;

        Mockito.doReturn(Optional.of(lecture))
                .when(lectureRepository)
                .findById(existingId);

        // getLectureById method
        Assertions.assertThrows(IllegalStateException.class,
                () -> lectureService.getLectureById(null));
        Assertions.assertThrows(IllegalStateException.class,
                () -> lectureService.getLectureById(fakeId));

        // updateLecture method
        Assertions.assertThrows(IllegalStateException.class,
                () -> lectureService.updateLecture(null, lecture));
        Assertions.assertThrows(IllegalStateException.class,
                () -> lectureService.updateLecture(fakeId, lecture));

        // deleteLecture method
        Assertions.assertThrows(IllegalStateException.class,
                () -> lectureService.deleteLecture(null));
        Assertions.assertThrows(IllegalStateException.class,
                () -> lectureService.deleteLecture(fakeId));
    }

    @Test
    void shouldThrowExceptionOnLocalDateIsNotTomorrowOrLaterSetting() {
        Lecture lecture = new Lecture();
        lecture.setId(1L);

        Mockito.doReturn(Optional.of(lecture))
                .when(lectureRepository)
                .findById(lecture.getId());

        Mockito.doReturn(lecture)
                .when(lectureRepository)
                .save(lecture);

        LocalDate invalidDate1 = LocalDate.now();
        lecture.setLocalDate(invalidDate1);
        Assertions.assertThrows(IllegalStateException.class,
                () -> lectureService.createLecture(lecture));
        Assertions.assertThrows(IllegalStateException.class,
                () -> lectureService.updateLecture(lecture.getId(), lecture));

        LocalDate invalidDate2 = LocalDate.now().minusDays(1);
        lecture.setLocalDate(invalidDate2);
        Assertions.assertThrows(IllegalStateException.class,
                () -> lectureService.createLecture(lecture));
        Assertions.assertThrows(IllegalStateException.class,
                () -> lectureService.updateLecture(lecture.getId(), lecture));

        LocalDate validDate = LocalDate.now().plusDays(1);
        lecture.setLocalDate(validDate);
        Assertions.assertDoesNotThrow(
                () -> lectureService.createLecture(lecture));
        Assertions.assertDoesNotThrow(
                () -> lectureService.updateLecture(lecture.getId(), lecture));
    }

    @Test
    void shouldSetInverseDependenciesDuringCreating() {
        Lecture lecture = new Lecture();

//        Audience audience = new Audience();
//        audience.setId(1L);
//        audience.setRoomNumber(100);
//        lecture.setAudience(audience);

        Course course = new Course();
        course.setId(1L);
        course.setSubject(Course.Subject.MATH);
        course.setTeacherFullName("Alice Alison");
        course.setLectures(new ArrayList<>());
        lecture.setCourse(course);

        Mockito.doReturn(lecture)
                .when(lectureRepository)
                .save(lecture);

        lectureService.createLecture(lecture);

        Assertions.assertTrue(course.getLectures().stream()
                .anyMatch(lecture::equals));

    }

    @Test
    void shouldSetDirectAndInverseDependenciesDuringUpdating() {
        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setLocalDate(LocalDate.now().plusDays(1));

        Long id = 1L;

        Audience audience = new Audience();
        audience.setId(1L);
        audience.setRoomNumber(100);
        lecture.setAudience(audience);

        Course course = new Course();
        course.setId(1L);
        course.setSubject(Course.Subject.MATH);
        course.setTeacherFullName("Alice Alison");
        course.setLectures(new ArrayList<>(Arrays.asList(new Lecture())));
        lecture.setCourse(course);

        Lecture emptyLecture = new Lecture();
        Mockito.doReturn(Optional.of(emptyLecture))
                .when(lectureRepository)
                .findById(id);

        lectureService.updateLecture(id, lecture);

        // direct
        Assertions.assertEquals(lecture, emptyLecture);

        // inverse
        Assertions.assertTrue(course.getLectures().stream()
                .anyMatch(emptyLecture::equals));

    }

    @Test
    void shouldUnsetInverseDependenciesDuringDeleting() {
        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setLocalDate(LocalDate.now().plusDays(1));

        Long id = 1L;

//        Audience audience = new Audience();
//        audience.setId(1L);
//        audience.setRoomNumber(100);
//        lecture.setAudience(audience);

        Course course = new Course();
        course.setId(1L);
        course.setSubject(Course.Subject.MATH);
        course.setTeacherFullName("Alice Alison");
        course.setLectures(new ArrayList<>(Arrays.asList(lecture, new Lecture())));
        lecture.setCourse(course);

        Mockito.doReturn(Optional.of(lecture))
                .when(lectureRepository)
                .findById(id);

        lectureService.deleteLecture(id);

        Assertions.assertFalse(
                course.getLectures().contains(lecture)
        );

    }

    @Test
    void shouldDeleteAudienceDependencyFromCertainLectures() {
        Lecture lecture = new Lecture();
        Audience audience = new Audience();
        audience.setRoomNumber(100);
        lecture.setAudience(audience);

        Lecture lectureThatShouldRemainWithAudienceDependency = new Lecture();
        Audience fakeAudience = new Audience();
        fakeAudience.setRoomNumber(200);
        lectureThatShouldRemainWithAudienceDependency.setAudience(fakeAudience);

        Mockito.doReturn(new ArrayList<>(
                Arrays.asList(lecture, lectureThatShouldRemainWithAudienceDependency)
        ))
                .when(lectureRepository)
                .findAll();

        lectureService.deleteAudienceFromLectures(audience);

        Assertions.assertNull(lecture.getAudience());
        Assertions.assertEquals(
                fakeAudience,
                lectureThatShouldRemainWithAudienceDependency.getAudience()
        );


    }

    @Test
    void shouldCorrectlyConvertEntityToDtoAndBackwards() {
        Lecture emptyFieldLecture = new Lecture();

        LectureGetDto emptyLectureGetDto = lectureService
                .convertToLectureDto(emptyFieldLecture);

        LecturePostDto emptyLecturePostDto = new LecturePostDto();
        convertLectureGetDtoToPostDto(emptyLectureGetDto, emptyLecturePostDto);

        Lecture anotherEmptyFieldLecture = lectureService
                .convertToLecture(emptyLecturePostDto);

        Assertions.assertEquals(emptyFieldLecture, anotherEmptyFieldLecture);

        // ---------------------------------------------------------------

        Lecture fullFieldLecture = new Lecture();
        fullFieldLecture.setId(1L);
        fullFieldLecture.setLocalDate(LocalDate.now().plusDays(1));

        Audience audience = new Audience();
        audience.setId(1L);
        audience.setRoomNumber(100);
        fullFieldLecture.setAudience(audience);

        Course course = new Course();
        course.setId(1L);
        course.setSubject(Course.Subject.MATH);
        course.setTeacherFullName("Alice Alison");
        fullFieldLecture.setCourse(course);

        Mockito.doReturn(audience)
                .when(audienceService)
                .getAudienceById(audience.getId());

        Mockito.doReturn(course)
                .when(courseService)
                .getCourseById(course.getId());

        LectureGetDto fullLectureGetDto = lectureService
                .convertToLectureDto(fullFieldLecture);

        LecturePostDto fullLecturePostDto = new LecturePostDto();
        convertLectureGetDtoToPostDto(fullLectureGetDto, fullLecturePostDto);

        Lecture anotherFullFieldLecture = lectureService
                .convertToLecture(fullLecturePostDto);

        Assertions.assertEquals(fullFieldLecture, anotherFullFieldLecture);
        Assertions.assertEquals(audience, anotherFullFieldLecture.getAudience());
        Assertions.assertEquals(course, anotherFullFieldLecture.getCourse());

    }

    private void convertLectureGetDtoToPostDto(
            LectureGetDto lectureGetDto, LecturePostDto lecturePostDto) {

        LocalDate localDate = lectureGetDto.getLocalDate();
        lecturePostDto.setLocalDate(localDate);

        CourseGetDto course = lectureGetDto.getCourse();
        if (course != null) {
            lecturePostDto.setCourseId(course.getId());
        }

        AudienceGetDto audience = lectureGetDto.getAudience();
        if (audience != null) {
            lecturePostDto.setAudienceId(audience.getId());
        }

    }
}