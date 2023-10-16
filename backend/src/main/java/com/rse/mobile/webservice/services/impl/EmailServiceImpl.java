package com.rse.mobile.webservice.services.impl;

import com.rse.mobile.webservice.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

import static com.rse.mobile.webservice.helper.EmailUtils.getPasswordResetUrl;
import static com.rse.mobile.webservice.helper.EmailUtils.getVerificationUrl;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value("${VERIFY_EMAIL_HOST}")
    private String host;
    @Value("${TITLE}")
    private  String title;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String VERIFICATION_TEMPLATE = "verify-email-template";
    public static final String PASSWORD_RESET_TEMPLATE = "password-reset-template";
    private static final int EMAIL_PRIORITY = 1;
    private static final String SUBJECT_VERIFICATION = "New User Account Verification";
    public static final String TEXT_HTML_ENCODING = "text/html";
    private static final String PASSWORD_RESET_SUBJECT = "Password Reset Request for";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    @Async
    public void sendSimpleMailMessage(String name, String to, String token) {
        try {
            final String msg = "It's been a long day without you, my friend\n" +
                    "And I'll tell you all about it when I see you again";
            LOGGER.info("Sending simple mail message to: {}", to);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(SUBJECT_VERIFICATION);
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(to);
            mailMessage.setText(msg);

            mailSender.send(mailMessage);
            LOGGER.info("Simple mail message sent successfully to: {}", to);
        } catch (Exception e) {
            LOGGER.error("Error sending simple mail message to: {}", to, e);
            throw new RuntimeException("Error sending simple mail message to: " + to, e);
        }
    }

    @Override
    public void sendHtmlMailMessage(String name, String to, String token) {
        Context context = new Context();
        context.setVariables(Map.of("title", title, "name", name, "url", getVerificationUrl(host, token)));
        sendHtmlMail(to, SUBJECT_VERIFICATION,context, VERIFICATION_TEMPLATE);
    }

    @Override
    public void sendHtmlResetPasswordMailMessage(String name, String to, String resetToken) {
        Context context = new Context();
        context.setVariables(Map.of("title", title, "name", name, "url", getPasswordResetUrl(host, resetToken)));
        sendHtmlMail(to, PASSWORD_RESET_SUBJECT + title ,context,PASSWORD_RESET_TEMPLATE);
    }

    private void sendHtmlMail(String to, String subject,Context context,String template) {
        try {
            LOGGER.info("Sending HTML mail message to: {}", to);
            String text = templateEngine.process(template, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(EMAIL_PRIORITY);
            helper.setSubject(subject);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text, true);
            mailSender.send(message);
            LOGGER.info("HTML mail message sent successfully to: {}", to);
        } catch (Exception exception) {
            handleEmailSendingError(to, exception);
        }
    }

    private void handleEmailSendingError(String recipient, Exception exception) {
        LOGGER.error("Error sending HTML mail message to: {}", recipient, exception);
        throw new RuntimeException("Error sending HTML mail message to: " + recipient, exception);
    }

    private MimeMessage getMimeMessage() {
        return mailSender.createMimeMessage();
    }

    private String getContentId(String filename) {
        return "<" + filename + ">";
    }


}
