package com.rapidticket.show.domain.dto.request;

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
public class FunctionCreateRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Code is required.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Code must contain only letters and numbers.")
    @Size(min = 1, max = 6, message = "Code must be between 1 and 6 characters.")
    private String code;

    @NotNull(message = "showCode is required.")
    private String showCode;

    @NotNull(message = "venueCode is required.")
    private String venueCode;

    @NotNull(message = "Date is required.")
    private Date date;

    @NotNull(message = "Base price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Base price must be greater than zero.")
    private Double basePrice;

    @NotNull(message = "Currency is required.")
    @Pattern(regexp = "^(USD)$", message = "Currency must be 'USD' as per the supported values.")
    private String currency;
}
