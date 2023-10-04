package com.rse.mobile.MobileWebservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public  class HttpResponse {
    protected LocalDateTime timestamp;
    protected int httpStatus;
    protected String httpCode;
    protected String path;
    protected String message;


}
