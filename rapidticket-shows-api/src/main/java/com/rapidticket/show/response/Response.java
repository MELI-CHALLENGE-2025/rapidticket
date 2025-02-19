package com.rapidticket.show.response;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import static com.rapidticket.show.utils.ShowConstantMessages.*;
import static com.rapidticket.show.utils.ConstantMessages.EMPTY_STRING;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Response<T> {
    private int status;
    private String userMessage;
    private String developerMessage;
    private String errorCode;
    private String moreInfo;
    private T data;

    public Response() {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.userMessage = UEM001;
        this.developerMessage = DEM001;
        this.errorCode = EMPTY_STRING;
        this.moreInfo = EMPTY_STRING;
        this.data = null;
    }
}


