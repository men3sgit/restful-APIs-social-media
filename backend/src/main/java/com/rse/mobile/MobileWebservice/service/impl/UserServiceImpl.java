package com.rse.mobile.MobileWebservice.service.impl;

import com.rse.mobile.MobileWebservice.dto.mapper.UserDTOMapper;
import com.rse.mobile.MobileWebservice.exception.request.ApiAuthenticationRequestException;
import com.rse.mobile.MobileWebservice.exception.request.ApiRequestException;
import com.rse.mobile.MobileWebservice.model.requests.PasswordResetRequest;
import com.rse.mobile.MobileWebservice.model.requests.UpdateUserRequest;
import com.rse.mobile.MobileWebservice.model.entities.tokens.ConfirmationToken;
import com.rse.mobile.MobileWebservice.model.entities.tokens.PasswordResetToken;
import com.rse.mobile.MobileWebservice.model.entities.User;
import com.rse.mobile.MobileWebservice.dto.UserDTO;
import com.rse.mobile.MobileWebservice.repository.UserRepository;
import com.rse.mobile.MobileWebservice.service.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String USER_NOT_FOUND_MSG = "user with email %s not found.";
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ConfirmationService confirmationService;
    private final PasswordResetService passwordResetService;
    private final UserDTOMapper userDTOMapper;
    private final VerificationService verificationService;
    private final EmailService emailService;
    private final UserFollowerService userFollowerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Loading user by username: {}", username);
        return userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    String errorMsg = String.format(USER_NOT_FOUND_MSG, username);
                    LOGGER.error(errorMsg);
                    return new UsernameNotFoundException(errorMsg);
                });
    }

    @Override
    public UserDTO registerNewUser(User user) {
        LOGGER.info("Registering a new user with email: {}", user.getEmail());

        validateUserEmail(user.getEmail());
        saveUserToDatabaseWithPasswordDecoder(user);

        ConfirmationToken confirmationToken = generateAndSaveConfirmationToken(user);
        confirmationService.saveConfirmationToken(confirmationToken);
        sendConfirmationEmail(user.getFullName(), user.getEmail(), confirmationToken.getToken());
        LOGGER.info("Registration successful for user: {}", user.getEmail());

        return userDTOMapper.apply(user);
    }

    public void saveUserToDatabaseWithPasswordDecoder(User user) {
        try {
            // Decode the provided plain text password (this is not a recommended practice)
            String decodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(decodedPassword);
            // Save the user to the database
            userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("Error decoding password and saving user to the database: {}", e.getMessage(), e);
            throw new RuntimeException("Error saving user to the database");
        }
    }

    private void validateUserEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            LOGGER.error("Email already taken: {}", email);
            throw new ApiRequestException("Email taken");
        }
    }

    private ConfirmationToken generateAndSaveConfirmationToken(User user) {
        ConfirmationToken confirmationToken = confirmationService.generateConfirmationTokenWithUser(user);
        confirmationService.saveConfirmationToken(confirmationToken);
        return confirmationToken;
    }

    private void sendConfirmationEmail(String fullName, String email, String token) {
        // TODO: Implement your email sending logic here
        LOGGER.info("Sending confirmation token email to user: {}", email);
        // Implement your email sending logic using the provided parameters and the generated token
        try {
            verificationService.sendVerificationTokenByEmail(fullName, email, token);
            LOGGER.info("Verification: {}", email);
        } catch (Exception e) {
            LOGGER.error("Verification{}", email, e);
            throw new ApiRequestException("Verification");
        }
    }

    @Override
    public Boolean verifyConfirmationToken(String token) {
        try {
            LOGGER.info("Verifying user token: {}", token);
            User enabledUser = confirmationService.updateConfirmedToken(token);
            userRepository.save(enabledUser);
            LOGGER.info("User enabled: {}", enabledUser.getEmail());
        } catch (RuntimeException e) {
            LOGGER.error("Error verifying user token: {}", e.getMessage(), e);
            throw new ApiAuthenticationRequestException("User was active");
        }
        return Boolean.TRUE;
    }

    @Override
    public String processForgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        String token = passwordResetService.generatePasswordResetToken(user);
        sendResetPasswordTokenEmail(user.getFullName(), user.getEmail(), token);
        return token;
    }

    @Override
    public UserDTO updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ApiRequestException("Email doesn't exist"));
        Class<? extends User> userClass = existingUser.getClass();
        Field[] fields = userClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                String fieldName = field.getName();
                String requestValue = getFieldValue(updateUserRequest, fieldName);

                if (requestValue != null) {
                    Object parsedValue = parseValue(requestValue, field.getType());
                    field.set(existingUser, parsedValue);
                }
            } catch (Exception e) {
                // Handle any exceptions that may occur during reflection
            }
        }
        return userDTOMapper.apply(existingUser);
    }

    @Override
    public String resetPassword(PasswordResetRequest request) {
        PasswordResetToken passwordResetToken = passwordResetService.getPasswordResetToken(request.token());
        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
        passwordResetToken.setResetAt(LocalDateTime.now());
        passwordResetService.savePasswordResetToken(passwordResetToken);
        return "Update password Successful";
    }

    /**
     * Allows a user to follow another user in the social media application.
     * By invoking this method, a user can establish a following relationship
     * with another user, enabling them to view updates and activities of the
     * followed user on their feed.
     *
     * @param followedId     The unique identifier of the user who wants to follow another user.
     * @param followById The unique identifier of the user whom the follower intends to follow.
     * @throws EntityNotFoundException Thrown when either the follower or the user to follow is not found in the database.
     * @throws RuntimeException        Thrown when the user is already being followed by the follower.
     */
    @Override
    public void followUser(Long followedId, Long followById) {
        userFollowerService.followUser(followedId,followById);
    }

    @Override
    public void unfollowUser(Long followedId, Long followById) {
        userFollowerService.unfollowUser(followedId,followById);
    }

    @Override
    public List<UserDTO> getFollowers(Long userId) {
        return userFollowerService.getFollowers(userId);
    }

    private String getFieldValue(UpdateUserRequest updateUserRequest, String fieldName) {
        return switch (fieldName) {
            case "fullName" -> updateUserRequest.getFullName();
            case "phoneNumber" -> updateUserRequest.getPhoneNumber();
            case "birthDate" -> updateUserRequest.getBirthDate();
            case "password" -> updateUserRequest.getPassword();
            default -> null;
        };
    }

    private Object parseValue(String value, Class<?> fieldType) {
        if (fieldType.equals(String.class)) {
            return value;
        } else if (fieldType.equals(LocalDate.class)) {
            return LocalDate.parse(value);
        }
        // Add more data types as needed

        throw new IllegalArgumentException("Unsupported data type: " + fieldType);
    }

    private void sendResetPasswordTokenEmail(String fullName, String email, String resetToken) {
        try {
            emailService.sendHtmlResetPasswordMailMessage(fullName, email, resetToken);
            LOGGER.info("Reset password email sent successfully to user: {}", fullName);
        } catch (Exception e) {
            LOGGER.error("Failed to send reset password email to user: {}", fullName, e);
            throw new ApiRequestException("Failed to send reset password email");
        }
    }

}