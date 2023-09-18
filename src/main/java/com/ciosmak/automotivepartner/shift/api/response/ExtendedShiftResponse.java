package com.ciosmak.automotivepartner.shift.api.response;

import com.ciosmak.automotivepartner.photo.response.PhotoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class ExtendedShiftResponse
{
    String firstName;
    String lastName;
    String registrationNumber;
    Integer startMileage;
    Integer endMileage;
    Integer kilometersTraveled;
    BigDecimal lpg;
    BigDecimal petrol;
    List<PhotoResponse> startCarPhotos;
    List<PhotoResponse> endCarPhotos;
    PhotoResponse invoicePhoto;
    boolean isAccidentReported;
}
