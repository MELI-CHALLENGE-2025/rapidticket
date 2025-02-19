package com.rapidticket.show.validations;

import com.rapidticket.show.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.rapidticket.show.utils.ShowConstantMessages.*;

@Component
public class ShowValidation {
    private ShowValidation() {

    }

    public static void isNull(boolean isNull) throws CustomException {
        if (isNull) {
            throw new CustomException(DEM001, UEM001, HttpStatus.BAD_REQUEST.value(), null);
        }
    }
}
