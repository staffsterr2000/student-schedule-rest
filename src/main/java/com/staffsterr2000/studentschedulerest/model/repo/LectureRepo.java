package com.staffsterr2000.studentschedulerest.model.repo;

import com.staffsterr2000.studentschedulerest.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepo extends JpaRepository<Lecture, Long> {



}
