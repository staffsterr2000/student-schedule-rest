package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.entity.Audience;
import com.staffsterr2000.studentschedulerest.model.repo.AudienceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AudienceService {


    private final AudienceRepo audienceRepository;


    public Audience getAudienceById(Long id) {
        return audienceRepository.findById(id).
                orElseThrow(() -> new IllegalStateException(String.format("No such auditory with %d id.", id)));
    }

    public Audience getAudienceByRoomNumber(Integer roomNumber) {
        return audienceRepository.findByRoomNumber(roomNumber).
                orElseThrow(() -> new IllegalStateException(String.format("No such auditory with %d room number.", roomNumber)));
    }

    public List<Audience> getAudiences() {
        return audienceRepository.findAll();
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean createAudience(Audience audience) {
        boolean audienceIsAbsent = getAudiences().stream()
                .filter(a -> a.equals(audience))
                .findAny()
                .isEmpty();

        if (audienceIsAbsent)
            audienceRepository.save(audience);

        return audienceIsAbsent;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean updateAudience(Long id, Audience audience) {
        boolean audienceIsPresent =
                audienceRepository.existsById(id);

        if (audienceIsPresent) {
            audience.setId(id);
            audienceRepository.save(audience);
        }

        return audienceIsPresent;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean deleteAudience(Long id) {
        boolean audienceIsPresent =
                audienceRepository.existsById(id);

        if (audienceIsPresent) {
            audienceRepository.deleteById(id);
        }

        return audienceIsPresent;
    }

}
