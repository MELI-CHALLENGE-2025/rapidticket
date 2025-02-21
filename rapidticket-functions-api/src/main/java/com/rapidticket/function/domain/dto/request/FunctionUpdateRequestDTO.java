package com.rapidticket.function.domain.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.io.Serial;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FunctionUpdateRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Size(max = 100, message = "The showCode must be up to 100 characters.")
    private String showCode;

    @Size(max = 255, message = "The venueCode must be up to 255 characters.")
    private String venueCode;

    private Date date;

    @DecimalMin(value = "0.0", inclusive = false, message = "The base price must be greater than 0.")
    private Double basePrice;

    @Pattern(regexp = "^(USD)$", message = "Currency must be 'USD' as per the supported values.")
    private String currency;
}
