package com.staffsterr2000.studentschedulerest.model.repo;

import com.staffsterr2000.studentschedulerest.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentGroupRepo extends JpaRepository<StudentGroup, Long> {

    Optional<StudentGroup> findByName(String name);

    boolean existsByName(String name);

}
