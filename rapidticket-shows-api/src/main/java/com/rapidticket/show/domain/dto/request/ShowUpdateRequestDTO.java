package com.rapidticket.show.domain.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowUpdateRequestDTO {
    @Size(max = 100, message = "Name must be up to 100 characters.")
    private String name;

    @Size(max = 1000, message = "The description must not exceed 1000 characters.")
    private String description;
}
