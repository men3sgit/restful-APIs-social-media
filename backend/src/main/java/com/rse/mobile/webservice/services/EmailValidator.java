package com.rse.mobile.webservice.services;

import java.util.function.Predicate;

public interface EmailValidator extends Predicate<String> {
    @Override
    boolean test(String s);
}
