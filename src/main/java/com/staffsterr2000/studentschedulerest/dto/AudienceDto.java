package com.staffsterr2000.studentschedulerest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AudienceDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("room")
    private Integer roomNumber;

}
