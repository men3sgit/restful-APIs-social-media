package com.rse.mobile.webservice.exceptions.request;

public class ApiRequestException extends RuntimeException{
    public ApiRequestException(String message) {
        super(message);
    }
}
