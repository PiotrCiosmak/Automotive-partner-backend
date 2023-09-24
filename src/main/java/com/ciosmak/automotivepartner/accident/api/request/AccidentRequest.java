package com.ciosmak.automotivepartner.accident.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class AccidentRequest
{
    private final List<String> accidentPhotosUrls;
    private final List<String> documentPhotoUrl;
    private final Optional<Integer> mileage;
    private final Optional<String> mileagePhotoUrl;
    private final Boolean isGuilty;
    private final Boolean isEndOfWork;
    private final Long shiftId;

    @JsonCreator
    public AccidentRequest(List<String> accidentPhotosUrls, List<String> documentPhotoUrl, Optional<Integer> mileage, Optional<String> mileagePhotoUrl, Boolean isGuilty, Boolean isEndOfWork, Long shiftId)
    {
        this.accidentPhotosUrls = accidentPhotosUrls;
        this.documentPhotoUrl = documentPhotoUrl;
        this.mileage = mileage;
        this.mileagePhotoUrl = mileagePhotoUrl;
        this.isGuilty = isGuilty;
        this.isEndOfWork = isEndOfWork;
        this.shiftId = shiftId;
    }
}
