package com.rse.mobile.webservice.payload.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticationRequest extends HttpRequest {
    private String email;
    private String password;
}
