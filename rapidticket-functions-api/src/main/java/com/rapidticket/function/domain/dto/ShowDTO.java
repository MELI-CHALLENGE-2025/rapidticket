package com.rapidticket.function.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ShowDTO implements Serializable {
    @NotBlank(message = "The code is required.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "The code must be alphanumeric on show.")
    @Size(min= 1, max = 6, message = "The code must not exceed 6 characters on show.")
    private String code;

    @NotBlank(message = "The name cannot be empty.")
    @Size(max = 255, message = "The name must not exceed 255 characters.")
    private String name;

    @NotBlank(message = "The description cannot be empty.")
    @Size(max = 1000, message = "The description must not exceed 1000 characters.")
    private String description;
}
