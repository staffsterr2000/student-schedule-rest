package com.staffsterr2000.studentschedulerest.model.repo;

import com.staffsterr2000.studentschedulerest.entity.Audience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AudienceRepo extends JpaRepository<Audience, Long> {

    Optional<Audience> findByRoomNumber(Integer roomNumber);

}
