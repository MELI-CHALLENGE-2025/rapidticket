package com.rapidticket.show.domain.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FunctionListRequestDTO {

    @Size(min = 1, max = 50, message = "The code must be between 1 and 50 characters.")
    private String code;

    @Size(max = 100, message = "The showCode must be up to 100 characters.")
    private String showCode;

    @Size(max = 255, message = "The venueCode must be up to 255 characters.")
    private String venueCode;

    @DecimalMin(value = "0.0", inclusive = false, message = "The minBasePrice must be greater than 0.")
    private Double minBasePrice;

    @DecimalMin(value = "0.0", inclusive = false, message = "The maxBasePrice must be greater than 0.")
    private Double maxBasePrice;

    @Size(min = 1, max = 3, message = "The currency must be a valid ISO 4217 currency code (1 to 3 characters).")
    private String currency;

    @Min(value = 1, message = "The page must be greater than or equal to 1.")
    private int page;

    @Min(value = 1, message = "The size must be greater than or equal to 1.")
    private int size;
}
