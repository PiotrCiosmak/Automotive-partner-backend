package com.ciosmak.automotivepartner.accident.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class AccidentRequest
{
    private final Boolean guilty;
    private final Boolean endOfWork;
    private final Long shiftId;

    @JsonCreator
    public AccidentRequest(Boolean guilty, Boolean endOfWork, Long shiftId)
    {
        this.guilty = guilty;
        this.endOfWork = endOfWork;
        this.shiftId = shiftId;
    }
}
