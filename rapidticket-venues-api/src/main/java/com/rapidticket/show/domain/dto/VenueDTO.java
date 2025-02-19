package com.rapidticket.show.domain.dto;

import jakarta.validation.constraints.Min;
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
public class VenueDTO implements Serializable {
    @NotBlank(message = "The code is required.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "The code must be alphanumeric on show.")
    @Size(min= 1, max = 6, message = "The code must not exceed 6 characters on show.")
    private String code;

    @NotBlank(message = "The name cannot be empty.")
    @Size(max = 255, message = "The name must not exceed 255 characters.")
    private String name;

    @Size(max = 500, message = "The location must not exceed 500 characters.")
    private String location;

    @Min(value = 1, message = "The capacity must be greater than or equal to 1.")
    private int capacity;
}
