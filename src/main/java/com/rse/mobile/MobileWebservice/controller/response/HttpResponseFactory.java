package com.rse.mobile.MobileWebservice.controller.response;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public class HttpResponseFactory {

    public static HttpResponse buildSuccessResponse(String message, String path) {
        return buildResponse(HttpStatus.OK, message, path);
    }

    public static HttpResponse buildNotFoundResponse(String message, String path) {
        return buildResponse(HttpStatus.NOT_FOUND, message, path);
    }

    public static HttpResponse buildErrorResponse(String message, String path) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, path);
    }

    private static HttpResponse buildResponse(HttpStatus httpStatus, String message, String path) {
        return HttpResponse.builder()
                .httpStatus(httpStatus.value())
                .httpCode(httpStatus.getReasonPhrase())
                .path(path)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
