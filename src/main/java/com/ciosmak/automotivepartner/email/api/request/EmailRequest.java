package com.ciosmak.automotivepartner.email.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class EmailRequest
{
    private final String email;

    @JsonCreator
    public EmailRequest(String email)
    {
        this.email = email;
    }
}
