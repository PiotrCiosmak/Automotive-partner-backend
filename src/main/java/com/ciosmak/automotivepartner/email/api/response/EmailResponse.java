package com.ciosmak.automotivepartner.email.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmailResponse
{
    private final Long id;
    private final String email;
}
