package com.rapidticket.function.validations;

import com.rapidticket.function.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.rapidticket.function.utils.VenueConstantMessages.*;


@Component
public class VenueValidation {
    private VenueValidation() {

    }

    public static void isNull(boolean isNull) throws CustomException {
        if (isNull) {
            throw new CustomException(DEM001, UEM001, HttpStatus.BAD_REQUEST.value(), null);
        }
    }
}
