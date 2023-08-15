package com.ciosmak.automotivepartner.accident.api.resposne;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccidentResponse
{
    private final Long id;
    private final Boolean isGuilty;
    private final Boolean isEndOfWork;
    private final Long shiftId;
}
