package com.rapidticket.register.validations;

import static com.rapidticket.register.utils.messages.RegisterConstantMessages.*;

import com.rapidticket.register.exception.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;

@Component
public class RegisterValidation {
    private RegisterValidation() {

    }

    public static void isExistsByEmail(boolean isExistsByEmail) throws CustomException {
        if (isExistsByEmail) {
            throw new CustomException(DEM002, UEM002, HttpStatus.BAD_REQUEST.value(), null);
        }
    }

    public static void isCreate(boolean isCreate) throws CustomException {
        if (!isCreate) {
            throw new CustomException(DEM004, UEM004, HttpStatus.BAD_REQUEST.value(), null);
        }
    }
}
