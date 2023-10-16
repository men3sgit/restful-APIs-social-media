package com.rse.mobile.webservice.payload.reponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse implements ResponseBody {
    private final LocalDateTime timestamp;
    private final HttpStatus httpStatus;
    private final String message;
    private final String errorDetails;

    public ErrorResponse(String message, String errorDetails, HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.httpStatus = status;
        this.message = message;
        this.errorDetails = errorDetails;
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