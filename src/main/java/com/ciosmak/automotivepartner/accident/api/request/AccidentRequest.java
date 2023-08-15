package com.ciosmak.automotivepartner.accident.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class AccidentRequest
{
    private final Boolean isGuilty;
    private final Boolean isEndOfWork;
    private final Long shiftId;

    @JsonCreator
    public AccidentRequest(Boolean isGuilty, Boolean isEndOfWork, Long shiftId)
    {
        this.isGuilty = isGuilty;
        this.isEndOfWork = isEndOfWork;
        this.shiftId = shiftId;
    }
}
