package com.rapidticket.show.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VenueListRequestDTO {

    @Size(min = 1, max = 50, message = "code must be between 1 and 50 characters")
    private String code;

    @Size(max = 100, message = "name must be up to 100 characters")
    private String name;

    @Size(max = 255, message = "location must be up to 255 characters")
    private String location;

    @Min(value = 1, message = "minCapacity must be greater than or equal to 1")
    private Integer minCapacity;

    @Min(value = 1, message = "maxCapacity must be greater than or equal to 1")
    private Integer maxCapacity;

    @Min(value = 1, message = "Page must be greater than or equal to 1")
    private int page;

    @Min(value = 1, message = "Size must be greater than or equal to 1")
    private int size;
}
