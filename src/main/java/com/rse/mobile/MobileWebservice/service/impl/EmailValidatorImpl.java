package com.rse.mobile.MobileWebservice.service.impl;

import com.rse.mobile.MobileWebservice.service.EmailValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmailValidatorImpl implements EmailValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final Pattern pattern;

    public EmailValidatorImpl() {
        pattern = Pattern.compile(EMAIL_REGEX);
    }

    @Override
    public boolean test(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

}
