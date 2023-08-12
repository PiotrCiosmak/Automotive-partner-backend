package com.ciosmak.automotivepartner.user.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class UserLoginDataRequest
{
    private final String email;
    private final String password;

    @JsonCreator
    public UserLoginDataRequest(String email, String password)
    {
        this.email = email;
        this.password = password;
    }
}
