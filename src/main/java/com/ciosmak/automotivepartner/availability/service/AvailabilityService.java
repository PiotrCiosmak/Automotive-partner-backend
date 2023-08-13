package com.ciosmak.automotivepartner.availability.service;

import com.ciosmak.automotivepartner.availability.api.request.AvailabilityRequest;
import com.ciosmak.automotivepartner.availability.api.response.AvailabilityResponse;
import com.ciosmak.automotivepartner.availability.domain.Availability;
import com.ciosmak.automotivepartner.availability.repository.AvailabilityRepository;
import com.ciosmak.automotivepartner.availability.support.AvailabilityMapper;
import com.ciosmak.automotivepartner.availability.support.Type;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AvailabilityService
{
    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;
    private final AvailabilityMapper availabilityMapper;

    public Boolean isSubmitted(Long userId)
    {
        userRepository.findById(userId).orElseThrow(UserExceptionSupplier.userNotFound(userId));
        Optional<Availability> existingAvailability = availabilityRepository.findByUser_IdAndDate(userId, getLastDayOfNextWeek());
        if (existingAvailability.isPresent())
        {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private LocalDate getLastDayOfNextWeek()
    {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusWeeks(1);

        int daysToAdd = DayOfWeek.SUNDAY.getValue() - nextWeek.getDayOfWeek().getValue();

        return nextWeek.plusDays(daysToAdd);
    }

    public AvailabilityResponse submit(AvailabilityRequest availabilityRequest)
    {
        Optional<Availability> existingAvailability = availabilityRepository.findByUser_IdAndDate(availabilityRequest.getUserId(), availabilityRequest.getDate());
       /* if (existingAvailability.isPresent())
        {
            throw AvailabilityExceptionSupplier.availabilitySubmitted(availabilityRequest.getUserId(), availabilityRequest.getDate()).get();
        }*/
        Availability availability = availabilityRepository.save(availabilityMapper.toAvailability(availabilityRequest));
        return availabilityMapper.toAvailabilityResponse(availability);
    }

    public Type getTypeOfAvailability(Long userId, LocalDate date)
    {
        userRepository.findById(userId).orElseThrow(UserExceptionSupplier.userNotFound(userId));
        // Availability existingAvailability = availabilityRepository.findByUser_IdAndDate(userId, date).orElseThrow(AvailabilityExceptionSupplier.availabilityNotFound(userId, date));
        // return existingAvailability.getType();
        return null;
    }

    public Integer getNumberOfApplicants(LocalDate date, Type type)
    {
        return availabilityRepository.countByDateAndType(date, type);
    }
}
