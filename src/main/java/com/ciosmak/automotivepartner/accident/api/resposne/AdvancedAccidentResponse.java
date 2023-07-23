package com.ciosmak.automotivepartner.accident.api.resposne;

import com.ciosmak.automotivepartner.availability.support.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class AdvancedAccidentResponse
{
    private final LocalDate date;
    private final Type type;
    private final String firstName;
    private final String lastName;
    private final String registrationNumber;
    private final Boolean guilty;
    private final Boolean endOfWork;
    private final Long shiftId;
}
