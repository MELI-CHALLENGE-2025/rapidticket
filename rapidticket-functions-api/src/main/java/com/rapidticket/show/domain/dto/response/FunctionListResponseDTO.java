package com.rapidticket.show.domain.dto.response;

import com.rapidticket.show.domain.dto.ShowDTO;
import com.rapidticket.show.domain.dto.VenueDTO;
import com.rapidticket.show.utils.enums.EnumCurrency;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class FunctionListResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "The code is required.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "The code must be alphanumeric on show.")
    @Size(min= 1, max = 6, message = "The code must not exceed 6 characters on show.")
    private String code;

    @NotNull(message = "The show is required.")
    @Size(max = 100, message = "The showCode must be up to 100 characters.")
    private ShowDTO show;

    @NotNull(message = "The venue is required.")
    @Size(max = 255, message = "The venueCode must be up to 255 characters.")
    private VenueDTO venue;

    @NotNull(message = "The date is required.")
    private Timestamp date;

    @NotNull(message = "The base price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "The base price must be greater than 0.")
    private Double basePrice;

    @NotNull(message = "The currency is required.")
    @Pattern(regexp = "^(USD)$", message = "Currency must be 'USD' as per the supported values.")
    private EnumCurrency currency;

}
