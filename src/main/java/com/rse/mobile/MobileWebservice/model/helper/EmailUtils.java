package com.rse.mobile.MobileWebservice.model.helper;

public class EmailUtils {

    public static String getEmailMessage(String name, String host, String token) {
        return "Hello " + name + ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n" +
                getVerificationUrl(host, token) + "\n\nThe support Team";
    }

    public static String getVerificationUrl(String host, String token) {
        return host + "/api/v1/auth/verify?token=" + token;
    }
    public static String getPasswordResetUrl(String host, String token) {
        return host + "/api/v1/users/password-reset?token=" + token;
    }
}