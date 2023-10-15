package com.ciosmak.automotivepartner.user.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class UserRestartPasswordRequest
{
    private final String token;
    private final String password;

    @JsonCreator
    public UserRestartPasswordRequest(String token, String password)
    {
        this.token = token;
        this.password = password;
    }
}
