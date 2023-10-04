package com.rse.mobile.MobileWebservice.service.impl;

import com.rse.mobile.MobileWebservice.dto.UserDTO;
import com.rse.mobile.MobileWebservice.dto.UserDTOMapper;
import com.rse.mobile.MobileWebservice.exception.ApiRequestException;
import com.rse.mobile.MobileWebservice.model.user.User;
import com.rse.mobile.MobileWebservice.repository.UserRepository;
import com.rse.mobile.MobileWebservice.request.UpdateUserRequest;
import com.rse.mobile.MobileWebservice.service.template.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;

// GenericUserUpdateService.java
@Service
@RequiredArgsConstructor
public class GenericUserUpdateServiceImpl implements UserUpdateService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

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


}
