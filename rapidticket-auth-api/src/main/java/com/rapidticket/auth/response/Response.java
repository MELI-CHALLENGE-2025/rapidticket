package com.rapidticket.auth.response;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

import static com.rapidticket.auth.utils.messages.ConstantMessages.*;

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
        this.userMessage = UEM000;
        this.developerMessage = DEM000;
        this.errorCode = EMPTY_STRING;
        this.moreInfo = EMPTY_STRING;
        this.data = null;
    }
}


