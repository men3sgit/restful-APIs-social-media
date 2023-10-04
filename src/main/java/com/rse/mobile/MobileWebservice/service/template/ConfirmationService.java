package com.rse.mobile.MobileWebservice.service.template;

import com.rse.mobile.MobileWebservice.model.token.ConfirmationToken;
import com.rse.mobile.MobileWebservice.model.user.User;

public interface ConfirmationService {
     void saveConfirmationToken(ConfirmationToken token);
     ConfirmationToken generateConfirmationTokenWithUser(User user);

     User updateConfirmedToken(String token);
}
