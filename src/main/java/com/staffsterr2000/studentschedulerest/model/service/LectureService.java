package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.AudienceGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.CourseGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.LecturePostDto;
import com.staffsterr2000.studentschedulerest.entity.Audience;
import com.staffsterr2000.studentschedulerest.entity.Course;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.model.repo.LectureRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
        return lectureRepository.save(lecture);
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
