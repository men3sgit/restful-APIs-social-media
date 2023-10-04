package com.rse.mobile.MobileWebservice.controller.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequest extends HttpRequest {
    private String email;
    private String password;
}
