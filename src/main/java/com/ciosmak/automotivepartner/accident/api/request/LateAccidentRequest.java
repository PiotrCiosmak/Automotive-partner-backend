package com.ciosmak.automotivepartner.accident.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.List;

@Getter
public class LateAccidentRequest
{
    private final List<String> accidentPhotosUrls;
    private final List<String> documentPhotoUrl;
    private final Boolean isGuilty;
    private final Long shiftId;

    @JsonCreator
    public LateAccidentRequest(List<String> accidentPhotosUrls, List<String> documentPhotoUrl, Boolean isGuilty, Long shiftId)
    {
        this.accidentPhotosUrls = accidentPhotosUrls;
        this.documentPhotoUrl = documentPhotoUrl;
        this.isGuilty = isGuilty;
        this.shiftId = shiftId;
    }
}
