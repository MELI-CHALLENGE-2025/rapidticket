package com.rapidticket.show.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class FunctionDTO implements Serializable {
    @NotBlank(message = "The code is required.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "The code must be alphanumeric on show.")
    @Size(min= 1, max = 6, message = "The code must not exceed 6 characters on show.")
    private String code;

    @NotNull(message = "The showCode is required.")
    @Size(max = 100, message = "The showCode must be up to 100 characters.")
    private String showCode;

    @NotNull(message = "The venueCode is required.")
    @Size(max = 255, message = "The venueCode must be up to 255 characters.")
    private String venueCode;

    @NotNull(message = "The date is required.")
    private Date date;

    @NotNull(message = "The base price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "The base price must be greater than 0.")
    private Double basePrice;

    @NotNull(message = "The currency is required.")
    @Size(min = 1, max = 3, message = "The currency must be a valid ISO 4217 currency code (1 to 3 characters).")
    private String currency;
}
