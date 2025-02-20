package com.rapidticket.auth.validations;

import static com.rapidticket.auth.utils.AuthConstantMessages.*;

import com.rapidticket.auth.exception.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;

@Component
public class AuthValidation {
    private AuthValidation() {

    }

    public static void isNull(boolean isNull) throws CustomException {
        if (isNull) {
            throw new CustomException(DEM001, UEM001, HttpStatus.BAD_REQUEST.value(), null);
        }
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

    public static void isVerifyPassword(boolean isVerifyPassword) throws  CustomException {
        if (!isVerifyPassword) {
            throw new CustomException(DEM005, UEM005, HttpStatus.BAD_REQUEST.value(), null);
        }
    }
}
