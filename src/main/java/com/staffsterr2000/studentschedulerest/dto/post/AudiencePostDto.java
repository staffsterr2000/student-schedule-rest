package com.staffsterr2000.studentschedulerest.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AudiencePostDto {

    @NotNull(message = "room number should be not null")
//    @Digits(integer = 3, fraction = 0, message = "room number must be unique and only with 3 digits")
    @JsonProperty("roomNumber")
    private Integer roomNumber;

}
