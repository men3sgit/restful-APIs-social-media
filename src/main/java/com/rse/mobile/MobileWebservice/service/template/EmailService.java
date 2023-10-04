package com.rse.mobile.MobileWebservice.service.template;

public interface EmailService {
    void sendSimpleMailMessage(String name, String to, String token);
    void sendHtmlMailMessage(String name, String to, String token);
}
