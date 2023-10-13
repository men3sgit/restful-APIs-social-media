package com.rse.mobile.MobileWebservice.dto;

import com.rse.mobile.MobileWebservice.model.entities.Role;

import java.time.LocalDate;

public record UserDTO (
        String email,
        String fullName,
        LocalDate dob,
        Role role


){
}
