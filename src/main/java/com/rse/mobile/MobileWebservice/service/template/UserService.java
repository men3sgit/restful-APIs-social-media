package com.rse.mobile.MobileWebservice.service.template;

import com.rse.mobile.MobileWebservice.model.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    String registerNewUser(User user);

    Boolean verifyConfirmationToken(String token);


    String processForgotPassword(String email);
}
