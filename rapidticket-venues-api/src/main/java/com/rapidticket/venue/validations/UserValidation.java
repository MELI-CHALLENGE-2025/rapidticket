package com.rapidticket.venue.validations;

import com.rapidticket.venue.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.rapidticket.venue.utils.messages.UserConstantMessages.DEM001;
import static com.rapidticket.venue.utils.messages.UserConstantMessages.UEM001;

@Component
public class UserValidation {
    private UserValidation() {

    }

    public static void isNull(boolean isNull) throws CustomException {
        if (isNull) {
            throw new CustomException(DEM001, UEM001, HttpStatus.BAD_REQUEST.value(), null);
        }
    }
}
