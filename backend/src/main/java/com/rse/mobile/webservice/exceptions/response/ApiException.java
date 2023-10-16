package com.rse.mobile.webservice.exceptions.response;

import org.springframework.http.HttpStatus;

public record ApiException(String message, int statusCode,HttpStatus httpStatus, String timeStamp) {

}
