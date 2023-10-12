package com.rse.mobile.MobileWebservice.model.requests;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String fullName;
    private String phoneNumber;
    private String birthDate;
    private String password;
}