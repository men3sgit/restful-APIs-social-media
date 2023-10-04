package com.rse.mobile.MobileWebservice.request;

import com.rse.mobile.MobileWebservice.model.user.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private String fullName;
    private String phoneNumber;
    private String birthDate;
    private String password;
}