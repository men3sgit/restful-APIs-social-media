package com.rse.mobile.MobileWebservice.exception.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiException(String message, int statusCode,HttpStatus httpStatus, String timeStamp) {

}
