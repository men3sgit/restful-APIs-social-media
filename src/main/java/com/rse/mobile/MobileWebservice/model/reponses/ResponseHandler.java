package com.rse.mobile.MobileWebservice.model.reponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class ResponseHandler {

    public ResponseEntity<ResponseBody> buildSuccessResponse(String message, Object data, HttpStatus status) {
        return ResponseEntity.status(status).body(new SuccessResponse(message, data, status));
    }



    public ResponseEntity<ResponseBody> buildSuccessResponse(Object data) {
        return buildSuccessResponse("Success", data, HttpStatus.OK);
    }
    public ResponseEntity<ResponseBody> buildSuccessResponse(String message,Object data) {
        return buildSuccessResponse(message, data, HttpStatus.OK);
    }

    public ResponseEntity<ResponseBody> buildErrorResponse(String message, String errorDetails, HttpStatus status) {
        return ResponseEntity.status(status).body(new ErrorResponse(message, errorDetails, status));
    }

    public ResponseEntity<ResponseBody> buildInternalServerErrorResponse(String message) {
        return buildErrorResponse("Internal Server Error", message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
