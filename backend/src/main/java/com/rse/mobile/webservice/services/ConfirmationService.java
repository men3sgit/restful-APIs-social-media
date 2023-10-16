package com.rse.mobile.webservice.services;

import com.rse.mobile.webservice.entities.tokens.ConfirmationToken;
import com.rse.mobile.webservice.entities.User;

public interface ConfirmationService {
     ConfirmationToken generateConfirmationToken(User user);
     Boolean validateToken(String token);

     User updateConfirmedToken(String token);


    void sendConfirmationEmail(String fullName, String email, String token);
}
