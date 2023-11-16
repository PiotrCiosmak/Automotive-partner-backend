package com.ciosmak.automotivepartner.availability.support;

import com.ciosmak.automotivepartner.availability.api.request.AvailabilityRequest;
import com.ciosmak.automotivepartner.availability.api.response.AvailabilityResponse;
import com.ciosmak.automotivepartner.availability.domain.Availability;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AvailabilityMapper
{
    private final UserRepository userRepository;

    public Availability toAvailability(AvailabilityRequest availabilityRequest)
    {
        return new Availability(availabilityRequest.getType(), availabilityRequest.getDate(), Boolean.FALSE, userRepository.findById(availabilityRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(availabilityRequest.getUserId())));
    }

    public Availability toAvailability(Availability availability, AvailabilityRequest availabilityRequest)
    {
        availability.setType(availabilityRequest.getType());
        availability.setDate(availabilityRequest.getDate());
        availability.setUser(userRepository.findById(availabilityRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(availabilityRequest.getUserId())));
        return availability;
    }

    public AvailabilityResponse toAvailabilityResponse(Availability availability)
    {
        return new AvailabilityResponse(availability.getId(), availability.getType(), availability.getDate(), availability.getIsUsed(), availability.getUser().getId());
    }
}
