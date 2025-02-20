package com.rapidticket.venue.domain.dto.request;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VenueUpdateRequestDTO {

    @Size(max = 100, message = "Name must be up to 100 characters.")
    private String name;

    @Size(max = 255, message = "Location must be up to 255 characters.")
    private String location;

    @Min(value = 1, message = "Capacity must be greater than or equal to 1.")
    private Integer capacity;
}
