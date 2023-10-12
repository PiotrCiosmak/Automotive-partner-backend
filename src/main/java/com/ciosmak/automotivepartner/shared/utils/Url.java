package com.ciosmak.automotivepartner.shared.utils;

import jakarta.servlet.http.HttpServletRequest;

public class Url
{
    public static String applicationUrl(HttpServletRequest request)
    {
        String appUrl = request.getRequestURL().toString();
        return appUrl.replace(request.getServletPath(), "");
        //TODO nie wiem która wersja
        //return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
