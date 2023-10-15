package com.rse.mobile.MobileWebservice.service;

import com.rse.mobile.MobileWebservice.exception.request.ApiRequestException;
import com.rse.mobile.MobileWebservice.model.entities.tokens.ConfirmationToken;
import com.rse.mobile.MobileWebservice.model.entities.User;

public interface ConfirmationService {
     ConfirmationToken generateConfirmationToken(User user);
     Boolean validateToken(String token);

     User updateConfirmedToken(String token);


    void sendConfirmationEmail(String fullName, String email, String token);
}
