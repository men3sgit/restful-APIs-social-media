package com.rse.mobile.MobileWebservice.service;

public interface EmailService {
    void sendSimpleMailMessage(String name, String to, String token);
    void sendHtmlMailMessage(String name, String to, String token);
    void sendHtmlResetPasswordMailMessage(String name, String to, String resetToken);


}
