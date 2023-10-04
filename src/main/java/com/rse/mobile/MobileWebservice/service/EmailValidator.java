package com.rse.mobile.MobileWebservice.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final Pattern pattern;

    public EmailValidator() {
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
