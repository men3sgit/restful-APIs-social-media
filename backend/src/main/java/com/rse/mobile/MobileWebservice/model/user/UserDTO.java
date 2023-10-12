package com.rse.mobile.MobileWebservice.model.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDTO (
        String email,
        String fullName,
        LocalDate dob,
        Role role


){
}
