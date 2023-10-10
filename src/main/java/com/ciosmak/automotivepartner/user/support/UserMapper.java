package com.ciosmak.automotivepartner.user.support;

import com.ciosmak.automotivepartner.user.api.request.UserRequest;
import com.ciosmak.automotivepartner.user.api.response.UserResponse;
import com.ciosmak.automotivepartner.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserMapper
{
    private final PasswordEncoder passwordEncoder;

    public User toUser(UserRequest userRequest)
    {
        return new User(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), passwordEncoder.encode(userRequest.getPassword()), userRequest.getPhoneNumber(), userRequest.getRole(), userRequest.getIsEnabled(), userRequest.getIsBlocked());
    }

    public User toUser(User user, UserRequest userRequest)
    {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setIsEnabled(userRequest.getIsEnabled());
        user.setIsBlocked(userRequest.getIsBlocked());
        return user;
    }

    public User toEnabledUser(User user)
    {
        user.setIsEnabled(true);
        return user;
    }

    public UserResponse toUserResponse(User user)
    {
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhoneNumber(), user.getRole(), user.getIsEnabled(), user.getIsBlocked());
    }
}
