package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.entity.Lecture;
import com.staffsterr2000.studentschedulerest.model.repo.LectureRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LectureService {

    private final LectureRepo lectureRepository;


    public List<Lecture> getLectures() {
        return lectureRepository.findAll();
    }

    public void addLecture(Lecture lecture) {
        lectureRepository.save(lecture);
    }

}
