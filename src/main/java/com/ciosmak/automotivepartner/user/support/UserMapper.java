package com.ciosmak.automotivepartner.user.support;

import com.ciosmak.automotivepartner.user.api.request.UserRequest;
import com.ciosmak.automotivepartner.user.api.response.UserResponse;
import com.ciosmak.automotivepartner.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper
{
    public User toUser(UserRequest userRequest)
    {
        return new User(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), userRequest.getPassword(), userRequest.getPhoneNumber(), userRequest.getRole(), userRequest.getIsEnabled(), userRequest.getIsBlocked());
    }

    public User toUser(User user, UserRequest userRequest)
    {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setRole(user.getRole());
        user.setIsEnabled(user.getIsEnabled());
        user.setIsBlocked(user.getIsBlocked());
        return user;
    }

    public UserResponse toUserResponse(User user)
    {
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhoneNumber(), user.getRole(), user.getIsEnabled(), user.getIsBlocked());
    }
}
