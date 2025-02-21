package com.rapidticket.show.validations;

import static com.rapidticket.show.utils.messages.ShowConstantMessages.*;

import com.rapidticket.show.exception.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;

@Component
public class ShowValidation {
    private ShowValidation() {

    }

    public static void isNull(boolean isNull) throws CustomException {
        if (isNull) {
            throw new CustomException(DEM001, UEM001, HttpStatus.BAD_REQUEST.value(), null);
        }
    }

    public static void isExistsByCode(boolean isExistsByCode) throws CustomException {
        if (isExistsByCode) {
            throw new CustomException(DEM002, UEM002, HttpStatus.BAD_REQUEST.value(), null);
        }
    }

    public static void isNotExistsByCode(boolean isExistByCode) throws CustomException {
        if (!isExistByCode) {
            throw new CustomException(DEM003, UEM003, HttpStatus.BAD_REQUEST.value(), null);
        }
    }

    public static void isCreate(boolean isCreate) throws CustomException {
        if (!isCreate) {
            throw new CustomException(DEM004, UEM004, HttpStatus.BAD_REQUEST.value(), null);
        }
    }

    public static void isUpdate(boolean isUpdate) throws CustomException {
        if (!isUpdate) {
            throw new CustomException(DEM005, UEM005, HttpStatus.BAD_REQUEST.value(), null);
        }
    }

    public static void isDelete(boolean isDelete) throws CustomException {
        if (!isDelete) {
            throw new CustomException(DEM006, UEM006, HttpStatus.BAD_REQUEST.value(), null);
        }
    }
}
