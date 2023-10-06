package com.rse.mobile.MobileWebservice.service.template;

public interface VerificationService {
 

    void sendVerificationCodeByEmail(String name, String email, String verificationCode);

    void sendVerificationTokenByEmail(String name, String email, String token);
}
