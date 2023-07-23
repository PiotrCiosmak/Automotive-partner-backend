package com.ciosmak.automotivepartner.accident.api.resposne;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccidentResponse
{
    private final Long id;
    private final Boolean guilty;
    private final Boolean endOfWork;
    private final Long shiftId;
}
