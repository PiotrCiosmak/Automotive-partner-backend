package com.ciosmak.automotivepartner.shift.api.response;

import com.ciosmak.automotivepartner.photo.response.PhotoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class ExtendedShiftResponse
{
    private final String firstName;
    private final String lastName;
    private final LocalDate date;
    private final String registrationNumber;
    private final Integer startMileage;
    private final Integer endMileage;
    private final Integer kilometersTraveled;
    private final BigDecimal lpg;
    private final BigDecimal petrol;
    private final List<PhotoResponse> startCarPhotos;
    private final List<PhotoResponse> endCarPhotos;
    private final PhotoResponse invoicePhoto;
    private final boolean isAccidentReported;
}
