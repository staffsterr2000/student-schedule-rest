package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.AudienceDto;
import com.staffsterr2000.studentschedulerest.dto.CourseDto;
import com.staffsterr2000.studentschedulerest.dto.LectureDto;
import com.staffsterr2000.studentschedulerest.dto.LectureGetDto;
import com.staffsterr2000.studentschedulerest.entity.Audience;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.model.repo.LectureRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LectureService {

    private final LectureRepo lectureRepository;
    private final AudienceService audienceService;
    private final CourseService courseService;
    private final ModelMapper modelMapper;

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

    // raw create
    @Transactional
    public void createLecture(LectureDto lectureDto) {
        Lecture lecture = convertToLecture(lectureDto);
        lectureRepository.save(lecture);
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

    private Lecture convertToLecture(LectureDto lectureDto) {
        LectureGetDto lectureGetDto =
                convertLecturePostToGetDto(lectureDto);

        return modelMapper.map(lectureGetDto, Lecture.class);
    }

    private LectureGetDto convertLecturePostToGetDto(LectureDto lectureDto) {
//        LectureGetDto lectureGetDto = modelMapper
//                .map(lectureDto, LectureGetDto.class);
        LectureGetDto lectureGetDto = new LectureGetDto();
        lectureGetDto.setLocalDate(lectureDto.getLocalDate());

        Long audienceId = lectureDto.getAudienceId();
        if (audienceId != null) {
            AudienceDto audienceDto = audienceService.getAudienceById(audienceId);
            lectureGetDto.setAudience(audienceDto);
        }

        Long courseId = lectureDto.getCourseId();
        if (courseId != null) {
            CourseDto courseDto = courseService.getCourseById(courseId);
            lectureGetDto.setCourse(courseDto);
        }

        return lectureGetDto;
    }

}
