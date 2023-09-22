package com.ciosmak.automotivepartner.accident.api.resposne;

import com.ciosmak.automotivepartner.shift.support.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ExtendedAccidentResponse
{
    private final LocalDate date;
    private final Type type;
    private final String firstName;
    private final String lastName;
    private final String registrationNumber;
    private final Boolean isGuilty;
    private final Long shiftId;
}
