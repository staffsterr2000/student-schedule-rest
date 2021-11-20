package com.staffsterr2000.studentschedulerest.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
public class LecturePostDto {

    @NotNull(message = "local date should be not blank")
    @JsonProperty("localDate")
    private LocalDate localDate;

    @JsonProperty("courseId")
    private Long courseId;

    @JsonProperty("audienceId")
    private Long audienceId;

}
