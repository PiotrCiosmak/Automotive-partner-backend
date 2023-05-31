package com.ciosmak.automotivepartner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CarController
{
    @GetMapping
    @ResponseBody
    public String hello()
    {
        return "I'm Spring Boot controller";
    }
}
