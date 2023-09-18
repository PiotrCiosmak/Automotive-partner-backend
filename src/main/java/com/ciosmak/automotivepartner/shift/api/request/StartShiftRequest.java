package com.ciosmak.automotivepartner.shift.api.request;

import com.ciosmak.automotivepartner.shift.support.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class StartShiftRequest
{
    private final LocalDate date;
    private final Type type;
    private final List<String> startCarPhotosUrls;
    private final Integer startMileage;
    private final String startMileagePhotoUrl;
    private final Long userId;

    @JsonCreator
    public StartShiftRequest(LocalDate date, Type type, List<String> startCarPhotosUrls, Integer startMileage, String startMileagePhotoUrl, Long userId)
    {
        this.date = date;
        this.type = type;
        this.startCarPhotosUrls = startCarPhotosUrls;
        this.startMileage = startMileage;
        this.startMileagePhotoUrl = startMileagePhotoUrl;
        this.userId = userId;
    }
}
