package com.ciosmak.automotivepartner.accident.api.resposne;

import com.ciosmak.automotivepartner.shift.support.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class BaseAccidentResponse
{
    private final LocalDate date;
    private final Type type;
    private final String firstName;
    private final String lastName;
    private final Boolean isEndOfWork;
    private final Long shiftId;
}
