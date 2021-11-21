package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.AudienceGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.AudiencePostDto;
import com.staffsterr2000.studentschedulerest.entity.Audience;
import com.staffsterr2000.studentschedulerest.model.repo.AudienceRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AudienceService {

    private final AudienceRepo audienceRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AudienceService(AudienceRepo audienceRepository,
                           ModelMapper modelMapper) {

        this.audienceRepository = audienceRepository;
        this.modelMapper = modelMapper;
    }

    public Audience getAudienceById(Long id) {
        return audienceRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(String.format("No such audience with %d id.", id)));
    }

    public Audience getAudienceByRoomNumber(Integer roomNumber) {
        return audienceRepository.findByRoomNumber(roomNumber).
                orElseThrow(() -> new IllegalStateException(String.format("No such audience with %d room number.", roomNumber)));
    }

    public List<Audience> getAudiences() {
        return audienceRepository.findAll();
    }

    @Transactional
    public Audience createAudience(Audience audience) {
        Integer audienceRoomNumber = audience.getRoomNumber();
        boolean audienceExists = audienceRepository
                .existsByRoomNumber(audienceRoomNumber);

        if (audienceExists) {
            throw new IllegalStateException(
                    String.format("Audience with room number %d already exists", audienceRoomNumber)
            );
        }

        return audienceRepository.save(audience);
    }

    @Transactional
    public void updateAudience(Long audienceId, Audience audience) {
        Integer audienceRoomNumber = audience.getRoomNumber();
        boolean audienceExists = audienceRepository
                .existsByRoomNumber(audienceRoomNumber);
        if (audienceExists) {
            throw new IllegalStateException(
                    String.format("Audience with room number %d already exists", audienceRoomNumber)
            );
        }

        Audience audienceFromDb = audienceRepository.findById(audienceId)
                .orElseThrow(() -> new IllegalStateException(
                        String.format("Audience with id %d doesn't exist", audienceId)
                ));

        if (audienceRoomNumber != null) {
            audienceFromDb.setRoomNumber(audienceRoomNumber);
        }

    }

    @Transactional
    public void deleteAudience(Long audienceId) {
        boolean audienceExists =
                audienceRepository.existsById(audienceId);

        if (!audienceExists) {
            throw new IllegalStateException(String.format("Audience with id %d doesn't exist", audienceId));
        }

        audienceRepository.deleteById(audienceId);
    }

    public AudienceGetDto convertToAudienceDto(Audience audience) {
        return modelMapper.map(audience, AudienceGetDto.class);
    }

    public Audience convertToAudience(AudiencePostDto audiencePostDto) {
        return modelMapper.map(audiencePostDto, Audience.class);
    }

}
