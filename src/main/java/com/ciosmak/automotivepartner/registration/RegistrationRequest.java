package com.ciosmak.automotivepartner.registration;

public record RegistrationRequest(String firstName, String lastName, String email, String password, String phoneNumber, String role)
{
}
