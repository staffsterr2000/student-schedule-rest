package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.AudienceDto;
import com.staffsterr2000.studentschedulerest.entity.Audience;
import com.staffsterr2000.studentschedulerest.model.repo.AudienceRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AudienceService {

    private final AudienceRepo audienceRepository;
    private final ModelMapper modelMapper;


    public AudienceDto getAudienceById(Long id) {
        Audience audienceById = audienceRepository.findById(id).
                orElseThrow(() -> new IllegalStateException(String.format("No such auditory with %d id.", id)));
        return convertToAudienceDto(audienceById);
    }

    public AudienceDto getAudienceByRoomNumber(Integer roomNumber) {
        Audience audienceByRoomNumber = audienceRepository.findByRoomNumber(roomNumber).
                orElseThrow(() -> new IllegalStateException(String.format("No such auditory with %d room number.", roomNumber)));
        return convertToAudienceDto(audienceByRoomNumber);
    }

    public List<AudienceDto> getAudiences() {
        return audienceRepository.findAll().stream()
                .map(this::convertToAudienceDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createAudience(AudienceDto audienceDto) {
        Integer audienceRoomNumber = audienceDto.getRoomNumber();
        boolean audienceExists = audienceRepository
                .findByRoomNumber(audienceRoomNumber).isPresent();

        if (audienceExists) {
            throw new IllegalStateException(
                    String.format("Audience with room number %d already exists", audienceRoomNumber)
            );
        }

        Audience audience = convertToAudience(audienceDto);
        return audienceRepository.save(audience)
                .getId();
    }

//    public void updateAudience(Long audienceId, Audience modifiedAudience) {
//        boolean audienceExists =
//                audienceRepository.existsById(audienceId);
//
//        if (audienceExists) {
//            modifiedAudience.setId(audienceId);
//            audienceRepository.save(modifiedAudience);
//        }
//    }

    @Transactional
    public void deleteAudience(Long audienceId) {
        boolean audienceExists =
                audienceRepository.existsById(audienceId);

        if (!audienceExists) {
            throw new IllegalStateException(String.format("Audience with id %d doesn't exist", audienceId));
        }

        audienceRepository.deleteById(audienceId);
    }

    private AudienceDto convertToAudienceDto(Audience audience) {
        return modelMapper.map(audience, AudienceDto.class);
    }

    private Audience convertToAudience(AudienceDto audienceDto) {
        return modelMapper.map(audienceDto, Audience.class);
    }

}
