package com.rse.mobile.MobileWebservice.service;

import com.rse.mobile.MobileWebservice.model.entities.tokens.ConfirmationToken;
import com.rse.mobile.MobileWebservice.model.entities.User;

public interface ConfirmationService {
     void saveConfirmationToken(ConfirmationToken token);
     ConfirmationToken generateConfirmationTokenWithUser(User user);

     User updateConfirmedToken(String token);
}
