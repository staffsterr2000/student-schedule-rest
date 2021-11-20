package com.staffsterr2000.studentschedulerest.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class StudentPostDto {

    @NotBlank(message = "first name should be not blank")
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank(message = "last name should be not blank")
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("studentGroupId")
    private Long studentGroupId;

}
