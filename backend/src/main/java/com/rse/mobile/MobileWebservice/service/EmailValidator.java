package com.rse.mobile.MobileWebservice.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public interface EmailValidator extends Predicate<String> {
    @Override
    boolean test(String s);
}
