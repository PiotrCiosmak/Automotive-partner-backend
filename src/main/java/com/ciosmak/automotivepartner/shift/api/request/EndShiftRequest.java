package com.ciosmak.automotivepartner.shift.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class EndShiftRequest
{
    private final Long id;
    private final List<String> endCarPhotosUrls;
    private final Integer endMileage;
    private final String endMileagePhotoUrl;
    private final BigDecimal lpg;
    private final BigDecimal petrol;
    private final String invoicePhotoUrl;

    @JsonCreator
    public EndShiftRequest(Long id, List<String> endCarPhotosUrls, Integer endMileage, String endMileagePhotoUrl, BigDecimal lpg, BigDecimal petrol, String invoicePhotoUrl)
    {
        this.id = id;
        this.endCarPhotosUrls = endCarPhotosUrls;
        this.endMileage = endMileage;
        this.endMileagePhotoUrl = endMileagePhotoUrl;
        this.lpg = lpg;
        this.petrol = petrol;
        this.invoicePhotoUrl = invoicePhotoUrl;
    }
}
