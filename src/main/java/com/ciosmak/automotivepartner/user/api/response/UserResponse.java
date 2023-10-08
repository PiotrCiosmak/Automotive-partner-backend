package com.ciosmak.automotivepartner.user.api.response;

import com.ciosmak.automotivepartner.user.support.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponse
{
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String phoneNumber;
    private final UserRole role;
    private final Boolean isEnabled;
    private final Boolean isBlocked;
}
