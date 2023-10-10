package com.ciosmak.automotivepartner.token.verification.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class VerificationTokenResponse
{
    private final Long id;
    private final String token;
    private final LocalDateTime expirationTime;
    private final Long userId;
}
