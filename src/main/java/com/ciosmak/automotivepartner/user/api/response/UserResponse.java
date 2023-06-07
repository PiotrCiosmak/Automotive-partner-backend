package com.ciosmak.automotivepartner.user.api.response;

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
    private final String role;
    private final Boolean enabled;
    private final Boolean blocked;
}
