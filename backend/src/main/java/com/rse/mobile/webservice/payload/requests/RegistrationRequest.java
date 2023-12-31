package com.rse.mobile.webservice.payload.requests;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegistrationRequest extends HttpRequest {
    private String email;
    private String password;
    private String fullName;
    private LocalDate dob;
}
