package com.staffsterr2000.studentschedulerest.dto.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AudienceGetDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("roomNumber")
    private Integer roomNumber;

}
