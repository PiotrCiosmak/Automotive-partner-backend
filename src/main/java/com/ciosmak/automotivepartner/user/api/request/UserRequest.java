package com.ciosmak.automotivepartner.user.api.request;

import com.ciosmak.automotivepartner.user.support.UserRole;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class UserRequest
{
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String phoneNumber;
    private final UserRole role;
    private final Boolean isEnabled;
    private final Boolean isBlocked;

    @JsonCreator
    public UserRequest(String firstName, String lastName, String email, String password, String phoneNumber, UserRole role, boolean isEnabled, boolean isBlocked)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.isEnabled = isEnabled;
        this.isBlocked = isBlocked;
    }
}
