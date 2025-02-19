package com.rapidticket.show.domain.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FunctionUpdateRequestDTO {

    @Size(max = 100, message = "The showId must be up to 100 characters.")
    private String showId;

    @Size(max = 255, message = "The venueId must be up to 255 characters.")
    private String venueId;

    private Date date;

    @DecimalMin(value = "0.0", inclusive = false, message = "The base price must be greater than 0.")
    private Double basePrice;

    @Size(min = 1, max = 3, message = "The currency must be a valid ISO 4217 currency code (1 to 3 characters).")
    private String currency;

}
