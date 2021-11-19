package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.LectureDto;
import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.model.repo.LectureRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LectureService {

    private final LectureRepo lectureRepository;
    private final ModelMapper modelMapper;

    public LectureDto getLectureById(Long lectureId) {
        Lecture lectureById = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalStateException(String.format("No such lecture with %d id.", lectureId)));
        return convertToLectureDto(lectureById);
    }

    public List<LectureDto> getLectures() {
        return lectureRepository.findAll().stream()
                .map(this::convertToLectureDto)
                .collect(Collectors.toList());
    }

    public void createLecture(Lecture lecture) {
        lectureRepository.save(lecture);
    }

    private LectureDto convertToLectureDto(Lecture lecture) {
        return modelMapper.map(lecture, LectureDto.class);
    }
}
