package com.rse.mobile.MobileWebservice.exception;

import com.rse.mobile.MobileWebservice.exception.request.ApiAuthenticationRequestException;
import com.rse.mobile.MobileWebservice.exception.response.ApiException;
import com.rse.mobile.MobileWebservice.exception.request.ApiRequestException;
import com.rse.mobile.MobileWebservice.model.helper.DateFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author amigoscode, menes implementation =))
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);
    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<?> handlerApiRequestException(ApiRequestException e) {
        ApiException error = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                DateFormatter.now()
        );
        LOGGER.error(error.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiAuthenticationRequestException.class)
    public ResponseEntity<?> handlerApiAuthenticationRequestException(ApiAuthenticationRequestException e) {
        String message = e.getMessage();
        HttpStatus status = message.contains("User is disabled") ? HttpStatus.FORBIDDEN : HttpStatus.UNAUTHORIZED;
        ApiException error = new ApiException(
                message,
                status.value(),
                status,
                DateFormatter.now()
        );
        LOGGER.error(error.toString());
        return new ResponseEntity<>(error, status);
    }

}
