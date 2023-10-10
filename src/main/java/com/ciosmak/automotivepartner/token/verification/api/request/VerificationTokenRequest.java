package com.ciosmak.automotivepartner.token.verification.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VerificationTokenRequest
{
    private final String token;
    private final LocalDateTime expirationTime;
    private final Long userId;

    @JsonCreator
    public VerificationTokenRequest(String token, LocalDateTime expirationTime, Long userId)
    {
        this.token = token;
        this.expirationTime = expirationTime;
        this.userId = userId;
    }
}
