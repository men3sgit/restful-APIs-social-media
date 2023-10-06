package com.rse.mobile.MobileWebservice.service.impl;

import com.rse.mobile.MobileWebservice.service.template.EmailService;
import com.rse.mobile.MobileWebservice.service.template.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final JavaMailSender mailSender;
    private final EmailService emailService;


    @Override
    public void sendVerificationCodeByEmail(String name,String email,String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + verificationCode);
        mailSender.send(message);

    }

    @Override
    public void sendVerificationTokenByEmail(String name, String email, String token) {
        emailService.sendHtmlMailMessage(name,email,token);
    }
}
