package com.staffsterr2000.studentschedulerest.model.repo;

import com.staffsterr2000.studentschedulerest.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {




}
