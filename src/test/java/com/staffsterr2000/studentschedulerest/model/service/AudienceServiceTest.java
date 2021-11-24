package com.staffsterr2000.studentschedulerest.model.service;

import com.staffsterr2000.studentschedulerest.dto.get.AudienceGetDto;
import com.staffsterr2000.studentschedulerest.dto.post.AudiencePostDto;
import com.staffsterr2000.studentschedulerest.entity.Audience;
import com.staffsterr2000.studentschedulerest.model.repo.AudienceRepo;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class AudienceServiceTest {

    @Autowired
    private AudienceService audienceService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private AudienceRepo audienceRepository;

    @Test
    void shouldThrowExceptionIfRoomNumberDoesNotExist() {
        Integer fakeRoomNumber = 1;

        Assertions.assertThrows(IllegalStateException.class,
                () -> audienceService.getAudienceByRoomNumber(fakeRoomNumber));
    }

    @Test
    void shouldReturnAudienceByRoomNumber() {
        Audience audience = new Audience();
        audience.setRoomNumber(100);

        Mockito.doReturn(Optional.of(audience))
                .when(audienceRepository)
                .findByRoomNumber(audience.getRoomNumber());

        Audience returnedAudience = audienceService
                .getAudienceByRoomNumber(audience.getRoomNumber());

        Assertions.assertSame(audience, returnedAudience);
    }

    @Test
    void shouldThrowExceptionOnAudienceWithExistingRoomNumberCreationAttempt() {
        Audience audience = new Audience();
        audience.setRoomNumber(1);

        Mockito.doReturn(true)
                .when(audienceRepository)
                .existsByRoomNumber(audience.getRoomNumber());

        Assert.assertThrows(IllegalStateException.class,
                () -> audienceService.createAudience(audience));
    }

    @Test
    void shouldThrowExceptionOnAudienceWithExistingRoomNumberUpdatingAttempt() {
        Audience audience = new Audience();
        audience.setRoomNumber(1);

        Mockito.doReturn(true)
                .when(audienceRepository)
                .existsByRoomNumber(audience.getRoomNumber());

        Assert.assertThrows(IllegalStateException.class,
                () -> audienceService.updateAudience(1L, audience));
    }

    @Test
    void shouldThrowExceptionIfGivenIdDoesNotExist() {
        Audience audience = new Audience();

        Long existingId = 1L;
        Long fakeId = 2L;

        Mockito.doReturn(Optional.of(audience))
                .when(audienceRepository)
                .findById(existingId);

        // getAudienceById method
        Assertions.assertThrows(IllegalStateException.class,
                () -> audienceService.getAudienceById(null));
        Assertions.assertThrows(IllegalStateException.class,
                () -> audienceService.getAudienceById(fakeId));

        // updateAudience method
        Assertions.assertThrows(IllegalStateException.class,
                () -> audienceService.updateAudience(null, audience));
        Assertions.assertThrows(IllegalStateException.class,
                () -> audienceService.updateAudience(fakeId, audience));

        // deleteAudience method
        Assertions.assertThrows(IllegalStateException.class,
                () -> audienceService.deleteAudience(null));
        Assertions.assertThrows(IllegalStateException.class,
                () -> audienceService.deleteAudience(fakeId));

    }

    @Test
    void shouldSetAllAudienceFieldsInUpdateMethod() {
        Audience audience = new Audience();
        audience.setId(1L);
        audience.setRoomNumber(100);

        Audience audienceFromDb = new Audience();

        Mockito.doReturn(Optional.of(audienceFromDb))
                .when(audienceRepository)
                .findById(audience.getId());

        audienceService.updateAudience(audience.getId(), audience);

        Assertions.assertEquals(audience, audienceFromDb);

    }

    // TODO: shouldSuccessfullyDeleteEntityFromDb
    @Test
    void shouldSuccessfullyDeleteEntityFromDb() {

    }

    @Test
    void shouldCorrectlyConvertEntityToDtoAndBackwards() {
        Audience emptyFieldAudience = new Audience();

        AudienceGetDto emptyAudienceGetDto = audienceService
                .convertToAudienceDto(emptyFieldAudience);

        AudiencePostDto emptyAudiencePostDto = new AudiencePostDto();
        convertAudienceGetDtoToPostDto(emptyAudienceGetDto, emptyAudiencePostDto);

        Audience anotherEmptyFieldAudience = audienceService
                .convertToAudience(emptyAudiencePostDto);

        Assertions.assertEquals(emptyFieldAudience, anotherEmptyFieldAudience);

        // ---------------------------------------------------------------

        Audience fullFieldAudience = new Audience();
        fullFieldAudience.setId(1L);
        fullFieldAudience.setRoomNumber(100);

        AudienceGetDto fullAudienceGetDto = audienceService
                .convertToAudienceDto(fullFieldAudience);

        AudiencePostDto fullAudiencePostDto = new AudiencePostDto();
        convertAudienceGetDtoToPostDto(fullAudienceGetDto, fullAudiencePostDto);

        Audience anotherFullFieldAudience = audienceService
                .convertToAudience(fullAudiencePostDto);

        Assertions.assertEquals(fullFieldAudience, anotherFullFieldAudience);

    }

    private void convertAudienceGetDtoToPostDto(
            AudienceGetDto audienceGetDto, AudiencePostDto audiencePostDto) {

        Integer roomNumber = audienceGetDto.getRoomNumber();
        audiencePostDto.setRoomNumber(roomNumber);

    }
}