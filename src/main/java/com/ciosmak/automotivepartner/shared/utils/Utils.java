package com.ciosmak.automotivepartner.shared.utils;

import jakarta.servlet.http.HttpServletRequest;

public class Utils
{
    public static String applicationUrl(HttpServletRequest request)
    {
        String appUrl = request.getRequestURL().toString();
        return appUrl.replace(request.getServletPath(), "");
    }

    public static String getClassName(RuntimeException exception)
    {
        String text = exception.getClass().toString();
        String[] parts = text.split("\\.");
        if (parts.length > 0)
        {
            return parts[parts.length - 1];
        }
        return "";
    }
}
