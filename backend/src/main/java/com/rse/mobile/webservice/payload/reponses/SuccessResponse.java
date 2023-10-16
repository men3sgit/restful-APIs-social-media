package com.rse.mobile.webservice.payload.reponses;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record SuccessResponse(LocalDateTime timestamp, String message, HttpStatus httpStatus,
                              Object data) implements ResponseBody {
    public SuccessResponse(String message, Object data, HttpStatus httpStatus) {
        this(LocalDateTime.now(), message, httpStatus, data);
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override   
    public int getStatusCode() {
        return httpStatus.value();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}


