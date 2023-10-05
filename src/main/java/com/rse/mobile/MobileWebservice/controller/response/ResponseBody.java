package com.rse.mobile.MobileWebservice.controller.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public interface ResponseBody {
    LocalDateTime getTimestamp();
    int getStatusCode();
    HttpStatus getHttpStatus();
    String getMessage();

}