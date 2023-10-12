package com.ciosmak.automotivepartner.shared.utils;

import java.time.LocalDateTime;

public class TokenExpirationTime
{
    private static final int DEFAULT_EXPIRATION_TIME = 10;

    public static LocalDateTime generateTokenExpirationTime()
    {
        LocalDateTime now = LocalDateTime.now();
        return now.plusMinutes(DEFAULT_EXPIRATION_TIME);
    }
}
