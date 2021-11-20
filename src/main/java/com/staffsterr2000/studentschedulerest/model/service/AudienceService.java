package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.AudienceGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.AudiencePostDto;
import com.staffsterr2000.studentschedulerest.entity.Audience;
import com.staffsterr2000.studentschedulerest.model.repo.AudienceRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    public AudienceGetDto getAudienceById(Long id) {
        Audience audienceById = audienceRepository.findById(id).
                orElseThrow(() -> new IllegalStateException(String.format("No such auditory with %d id.", id)));
        return convertToAudienceDto(audienceById);
    }

    public AudienceGetDto getAudienceByRoomNumber(Integer roomNumber) {
        Audience audienceByRoomNumber = audienceRepository.findByRoomNumber(roomNumber).
                orElseThrow(() -> new IllegalStateException(String.format("No such auditory with %d room number.", roomNumber)));
        return convertToAudienceDto(audienceByRoomNumber);
    }

    public List<AudienceGetDto> getAudiences() {
        return audienceRepository.findAll().stream()
                .map(this::convertToAudienceDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createAudience(AudiencePostDto audiencePostDto) {
        Integer audienceRoomNumber = audiencePostDto.getRoomNumber();
        boolean audienceExists = audienceRepository
                .findByRoomNumber(audienceRoomNumber).isPresent();

        if (audienceExists) {
            throw new IllegalStateException(
                    String.format("Audience with room number %d already exists", audienceRoomNumber)
            );
        }

        Audience audience = convertToAudience(audiencePostDto);
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

    private AudienceGetDto convertToAudienceDto(Audience audience) {
        return modelMapper.map(audience, AudienceGetDto.class);
    }

    private Audience convertToAudience(AudiencePostDto audiencePostDto) {
        return modelMapper.map(audiencePostDto, Audience.class);
    }

}
