package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.LecturePostDto;
import com.staffsterr2000.studentschedulerest.entity.Audience;
import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.model.repo.LectureRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class LectureService {

    private final LectureRepo lectureRepository;

    private final AudienceService audienceService;

    private final CourseService courseService;

    private final ModelMapper modelMapper;

    @Autowired
    public LectureService(LectureRepo lectureRepository,
                          @Lazy AudienceService audienceService,
                          @Lazy CourseService courseService,
                          ModelMapper modelMapper) {

        this.lectureRepository = lectureRepository;
        this.audienceService = audienceService;
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    public Lecture getLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalStateException(String.format("No such lecture with %d id.", lectureId)));
    }

    public List<Lecture> getLectures() {
        return lectureRepository.findAll();
    }

    @Transactional
    public Lecture createLecture(Lecture lecture) {
        LocalDate localDate = lecture.getLocalDate();
        if (localDate != null) {
            if (!localDate.isAfter(LocalDate.now())) {
                throw new IllegalStateException("Day of the lecture must be at least tomorrow.");
            }
        }

        Lecture savedLecture = lectureRepository.save(lecture);

        Course course = savedLecture.getCourse();
        if (course != null) {
            List<Lecture> lectures = course.getLectures();

            lectures.add(savedLecture);
        }

        return savedLecture;
    }

    @Transactional
    public Lecture updateLecture(Long lectureId, Lecture modifiedLecture) {
        Lecture lectureFromDb = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Lecture with id %d doesn't exist", lectureId)
                ));

        LocalDate modifiedDate = modifiedLecture.getLocalDate();
        if (modifiedDate != null) {
            if (!modifiedDate.isAfter(LocalDate.now())) {
                throw new IllegalStateException("Day of the lecture must be at least tomorrow.");
            }

            lectureFromDb.setLocalDate(modifiedDate);
        }

        Course modifiedCourse = modifiedLecture.getCourse();
        if (modifiedCourse != null) {
            lectureFromDb.setCourse(modifiedCourse);

            List<Lecture> lectures = modifiedCourse.getLectures();

            if (!lectures.contains(lectureFromDb))
                lectures.add(lectureFromDb);
        }

        Audience modifiedAudience = modifiedLecture.getAudience();
        if (modifiedAudience != null) {
            lectureFromDb.setAudience(modifiedAudience);
        }

        return lectureRepository.save(lectureFromDb);

    }

    @Transactional
    public void deleteLecture(Long lectureId) {
        boolean lectureExists =
                lectureRepository.existsById(lectureId);

        if (!lectureExists) {
            throw new IllegalStateException(String.format("Lecture with id %d doesn't exist", lectureId));
        }

        lectureRepository.deleteById(lectureId);
    }



    public LectureGetDto convertToLectureDto(Lecture lecture) {
        return modelMapper.map(lecture, LectureGetDto.class);
    }

    public Lecture convertToLecture(LecturePostDto lecturePostDto) {
        Lecture lecture = new Lecture();
        lecture.setLocalDate(lecturePostDto.getLocalDate());

        Long audienceId = lecturePostDto.getAudienceId();
        if (audienceId != null) {
            Audience audience = audienceService.getAudienceById(audienceId);
            lecture.setAudience(audience);
        }

        Long courseId = lecturePostDto.getCourseId();
        if (courseId != null) {
            Course course = courseService.getCourseById(courseId);

            lecture.setCourse(course);
        }

        return lecture;
    }

}
