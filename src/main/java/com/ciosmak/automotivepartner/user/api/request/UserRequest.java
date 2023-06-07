package com.ciosmak.automotivepartner.user.api.request;

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
    private final String role;
    private final Boolean enabled;
    private final Boolean blocked;

    @JsonCreator
    public UserRequest(String firstName, String lastName, String email, String password, String phoneNumber, String role, boolean enabled, boolean blocked)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.enabled = enabled;
        this.blocked = blocked;
    }
}
