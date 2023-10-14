package com.ciosmak.automotivepartner.shared.utils.token;

import com.ciosmak.automotivepartner.token.api.request.TokenRequest;
import com.ciosmak.automotivepartner.token.support.TokenType;
import com.ciosmak.automotivepartner.user.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class TokenUtils
{

    private static final int DEFAULT_EXPIRATION_TIME = 10;

    public static TokenRequest generateNewVerificationToken(User user)
    {
        return new TokenRequest(UUID.randomUUID().toString(), TokenType.VERIFICATION, generateTokenExpirationTime(), user.getId());
    }

    public static TokenRequest generateNewPasswordResetToken(User user)
    {
        return new TokenRequest(UUID.randomUUID().toString(), TokenType.PASSWORD_RESET, generateTokenExpirationTime(), user.getId());
    }

    public static LocalDateTime generateTokenExpirationTime()
    {
        LocalDateTime now = LocalDateTime.now();
        return now.plusMinutes(DEFAULT_EXPIRATION_TIME);
    }
}
