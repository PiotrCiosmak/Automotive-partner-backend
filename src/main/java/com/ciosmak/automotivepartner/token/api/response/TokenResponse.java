package com.ciosmak.automotivepartner.token.api.response;

import com.ciosmak.automotivepartner.token.support.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class TokenResponse
{
    private final Long id;
    private final String token;
    private final TokenType type;
    private final LocalDateTime expirationTime;
    private final Long userId;
}
