package com.rapidticket.function.domain.dto;

import com.rapidticket.function.utils.enums.EnumCurrency;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import java.io.Serial;
import java.util.Date;

@Getter
@Setter
@ToString
public class FunctionDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Code is required.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Code must be alphanumeric.")
    @Size(min = 1, max = 6, message = "Code must be between 1 and 6 characters long.")
    private String code;

    @NotNull(message = "Show information is required.")
    private ShowDTO show;

    @NotNull(message = "Venue information is required.")
    private VenueDTO venue;

    @NotNull(message = "Date is required.")
    private Date date;

    @NotNull(message = "Base price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Base price must be greater than 0.")
    private Double basePrice;

    @NotNull(message = "Currency is required.")
    @Pattern(regexp = "^(USD)$", message = "Currency must be 'USD' as per the supported values.")
    private EnumCurrency currency;
}
