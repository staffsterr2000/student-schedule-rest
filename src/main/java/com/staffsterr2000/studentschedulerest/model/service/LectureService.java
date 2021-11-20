package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.AudienceGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.CourseGetDto;
import com.staffsterr2000.studentschedulerest.dto.get.LectureGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.LecturePostDto;
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

    public LectureGetDto getLectureById(Long lectureId) {
        Lecture lectureById = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalStateException(String.format("No such lecture with %d id.", lectureId)));
        return convertToLectureDto(lectureById);
    }

    public List<LectureGetDto> getLectures() {
        return lectureRepository.findAll().stream()
                .map(this::convertToLectureDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createLecture(LecturePostDto lecturePostDto) {
        Lecture lecture = convertToLecture(lecturePostDto);
        return lectureRepository.save(lecture).getId();
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



    private LectureGetDto convertToLectureDto(Lecture lecture) {
        return modelMapper.map(lecture, LectureGetDto.class);
    }

    private Lecture convertToLecture(LecturePostDto lecturePostDto) {
        LectureGetDto lectureGetDto =
                transformAndFetchAllLectureDataToDto(lecturePostDto);

        return modelMapper.map(lectureGetDto, Lecture.class);
    }

    private LectureGetDto transformAndFetchAllLectureDataToDto(LecturePostDto lecturePostDto) {
        LectureGetDto lectureGetDto = new LectureGetDto();

        lectureGetDto.setLocalDate(lecturePostDto.getLocalDate());

        Long audienceId = lecturePostDto.getAudienceId();
        if (audienceId != null) {
            AudienceGetDto audienceGetDto = audienceService.getAudienceById(audienceId);
            lectureGetDto.setAudience(audienceGetDto);
        }

        Long courseId = lecturePostDto.getCourseId();
        if (courseId != null) {
            CourseGetDto courseGetDto = courseService.getCourseById(courseId);
            lectureGetDto.setCourse(courseGetDto);
        }

        return lectureGetDto;
    }

}
