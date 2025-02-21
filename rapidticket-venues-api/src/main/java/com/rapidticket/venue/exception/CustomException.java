package com.rapidticket.venue.exception;

import lombok.Getter;
import java.io.Serial;

@Getter
public class CustomException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6365652257268547172L;
    private final String userMessage;
    private final int status;

    public CustomException(String developerMessage, String userMessage, int status, Throwable e) {
        super(developerMessage, e);
        this.userMessage = userMessage;
        this.status = status;
    }
}
