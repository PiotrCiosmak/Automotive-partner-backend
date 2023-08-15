package com.ciosmak.automotivepartner.availability.service;

import com.ciosmak.automotivepartner.availability.api.request.AvailabilityRequest;
import com.ciosmak.automotivepartner.availability.api.request.WeekAvailabilityRequest;
import com.ciosmak.automotivepartner.availability.api.response.AvailabilityResponse;
import com.ciosmak.automotivepartner.availability.domain.Availability;
import com.ciosmak.automotivepartner.availability.repository.AvailabilityRepository;
import com.ciosmak.automotivepartner.availability.support.AvailabilityExceptionSupplier;
import com.ciosmak.automotivepartner.availability.support.AvailabilityMapper;
import com.ciosmak.automotivepartner.availability.support.Type;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AvailabilityService
{
    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;
    private final AvailabilityMapper availabilityMapper;

    @Transactional
    public List<AvailabilityResponse> submit(WeekAvailabilityRequest weekAvailabilityRequest)
    {
        Long userId = weekAvailabilityRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(UserExceptionSupplier.userNotFound(userId));

        ArrayList<LocalDate> weekDates = getDatesOfNextWeek();

        Optional<Availability> availabilityCandidate = availabilityRepository.findByUser_IdAndDate(userId, getFirstDayOfNextWeek());
        if (availabilityCandidate.isPresent())
        {
            throw AvailabilityExceptionSupplier.availabilityAlreadySubmitted(user.getFirstName(), user.getLastName()).get();
        }

        List<AvailabilityResponse> availabilityResponses = new ArrayList<>();
        for (int i = 0; i < 7; ++i)
        {
            AvailabilityRequest availabilityRequest = new AvailabilityRequest(weekAvailabilityRequest.getTypes().get(i), weekDates.get(i), weekAvailabilityRequest.getUserId());
            Availability availability = availabilityRepository.save(availabilityMapper.toAvailability(availabilityRequest));
            availabilityResponses.add(availabilityMapper.toAvailabilityResponse(availability));
        }
        return availabilityResponses;
    }

    private ArrayList<LocalDate> getDatesOfNextWeek()
    {
        LocalDate firstDayOfNextWeek = getFirstDayOfNextWeek();

        ArrayList<LocalDate> weekDates = new ArrayList<>();
        for (int i = 0; i < 7; ++i)
        {
            weekDates.add(firstDayOfNextWeek.plusDays(i));
        }
        return weekDates;
    }

    public Boolean isSubmitted(Long userId)
    {
        userRepository.findById(userId).orElseThrow(UserExceptionSupplier.userNotFound(userId));

        Optional<Availability> availabilityCandidate = availabilityRepository.findByUser_IdAndDate(userId, getFirstDayOfNextWeek());
        return availabilityCandidate.isPresent();
    }

    private LocalDate getFirstDayOfNextWeek()
    {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    }

    public Type getType(Long userId, LocalDate date)
    {
        User user = userRepository.findById(userId).orElseThrow(UserExceptionSupplier.userNotFound(userId));

        Availability availability = availabilityRepository.findByUser_IdAndDate(userId, date).orElseThrow(AvailabilityExceptionSupplier.incorrectDate(user.getFirstName(), user.getLastName(), date));
        return availability.getType();
    }

    public Integer getQuantity(LocalDate date, Type type)
    {
        return availabilityRepository.countByDateAndType(date, type);
    }
}
