package com.rse.mobile.MobileWebservice.service.impl;

import com.rse.mobile.MobileWebservice.service.template.EmailService;
import com.rse.mobile.MobileWebservice.service.template.VerificationService;
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
import java.util.Random;

import static com.rse.mobile.MobileWebservice.model.helper.EmailUtils.getVerificationUrl;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String EMAIL_TEMPLATE = "email-template";
    public static final String TEXT_HTML_ENCODING = "text/html";
    private static final String NEW_USER_VERIFICATION_SUBJECT = "New User Account Verification";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Value("${VERIFY_EMAIL_HOST}")
    private String host;
    @Value("${TITLE}")
    private String title;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    @Async
    public void sendSimpleMailMessage(String name, String to, String token) {
        try {
            final String msg = "It's been a long day without you, my friend\n" +
                    "And I'll tell you all about it when I see you again";
            LOGGER.info("Sending simple mail message to: {}", to);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(NEW_USER_VERIFICATION_SUBJECT);
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
        try {
            LOGGER.info("Sending HTML mail message to: {}", to);
            Context context = new Context();
            context.setVariables(Map.of("title", title, "name", name, "url", getVerificationUrl(host, token)));
            String text = templateEngine.process(EMAIL_TEMPLATE, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_VERIFICATION_SUBJECT);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text, true);

            mailSender.send(message);
            LOGGER.info("HTML mail message sent successfully to: {}", to);
        } catch (Exception exception) {
            LOGGER.error("Error sending HTML mail message to: {}", to, exception);
            throw new RuntimeException("Error sending HTML mail message to: " + to, exception);
        }
    }

    private MimeMessage getMimeMessage() {
        return mailSender.createMimeMessage();
    }

    private String getContentId(String filename) {
        return "<" + filename + ">";
    }



}
