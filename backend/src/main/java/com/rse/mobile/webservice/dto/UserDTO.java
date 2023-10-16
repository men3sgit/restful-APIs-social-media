package com.rse.mobile.webservice.dto;

import com.rse.mobile.webservice.entities.Role;

import java.time.LocalDate;

public record UserDTO (
        String email,
        String fullName,
        LocalDate dob,
        Role role


){
}
