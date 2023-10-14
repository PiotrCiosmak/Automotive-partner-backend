package com.ciosmak.automotivepartner.token.api.request;

import com.ciosmak.automotivepartner.token.support.TokenType;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TokenRequest
{
    private final String token;
    private final TokenType type;
    private final LocalDateTime expirationTime;
    private final Long userId;

    @JsonCreator
    public TokenRequest(String token, TokenType type, LocalDateTime expirationTime, Long userId)
    {
        this.token = token;
        this.type = type;
        this.expirationTime = expirationTime;
        this.userId = userId;
    }
}
