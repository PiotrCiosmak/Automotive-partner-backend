package com.ciosmak.automotivepartner.shift.service;

import com.ciosmak.automotivepartner.availability.domain.Availability;
import com.ciosmak.automotivepartner.availability.repository.AvailabilityRepository;
import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.car.repository.CarRepository;
import com.ciosmak.automotivepartner.photo.domain.Photo;
import com.ciosmak.automotivepartner.photo.repository.PhotoRepository;
import com.ciosmak.automotivepartner.photo.support.PhotoType;
import com.ciosmak.automotivepartner.shift.api.request.EndShiftRequest;
import com.ciosmak.automotivepartner.shift.api.request.GenerateShiftRequest;
import com.ciosmak.automotivepartner.shift.api.request.StartShiftRequest;
import com.ciosmak.automotivepartner.shift.api.request.UpdateFuelRequest;
import com.ciosmak.automotivepartner.shift.api.response.ExtendedShiftResponse;
import com.ciosmak.automotivepartner.shift.api.response.ShiftResponse;
import com.ciosmak.automotivepartner.shift.domain.Shift;
import com.ciosmak.automotivepartner.shift.repository.ShiftRepository;
import com.ciosmak.automotivepartner.shift.support.ShiftExceptionSupplier;
import com.ciosmak.automotivepartner.shift.support.ShiftMapper;
import com.ciosmak.automotivepartner.shift.support.Type;
import com.ciosmak.automotivepartner.statistic.api.request.StatisticsRequest;
import com.ciosmak.automotivepartner.statistic.api.request.StatisticsUpdateRequest;
import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import com.ciosmak.automotivepartner.statistic.repository.StatisticsRepository;
import com.ciosmak.automotivepartner.statistic.support.StatisticsMapper;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ShiftService
{
    private final ShiftRepository shiftRepository;
    private final ShiftMapper shiftMapper;
    private final StatisticsMapper statisticsMapper;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final AvailabilityRepository availabilityRepository;
    private final StatisticsRepository statisticsRepository;

    @Transactional
    public List<ShiftResponse> generate()
    {
        List<Shift> nextWeekShifts = shiftRepository.findAllByDate(getNextMonday());
        checkIfGenerationIsAllowed(nextWeekShifts);

        List<Shift> shifts = new ArrayList<>();
        for (var date : getNextWeekDates())
        {
            generateShiftsForDate(date, Type.DAY, shifts);
            generateShiftsForDate(date, Type.NIGHT, shifts);
        }

        List<ShiftResponse> shiftResponses = new ArrayList<>();
        for (var shift : shifts)
        {
            shiftResponses.add(shiftMapper.toShiftResponse(shift));
        }
        return shiftResponses;
    }

    private LocalDate getNextMonday()
    {
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    }

    private void checkIfGenerationIsAllowed(List<Shift> nextWeekShifts)
    {
        if (!nextWeekShifts.isEmpty())
        {
            throw ShiftExceptionSupplier.shiftsAlreadyGenerated().get();
        }

        if (!isWeekendToday())
        {
            throw ShiftExceptionSupplier.shiftsGeneratingTooEarly().get();
        }
    }

    public boolean isWeekendToday()
    {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

        return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private List<LocalDate> getNextWeekDates()
    {
        List<LocalDate> nextWeekDates = new ArrayList<>();
        LocalDate nextMonday = getNextMonday();

        for (int i = 0; i < 7; ++i)
        {
            nextWeekDates.add(nextMonday.plusDays(i));
        }
        return nextWeekDates;
    }

    private void generateShiftsForDate(LocalDate date, Type type, List<Shift> shifts)
    {
        List<Availability> availabilities = availabilityRepository.findAllByDateAndType(date, type);
        List<Car> cars = carRepository.findAllByIsBlocked(Boolean.FALSE);
        int shiftsToGenerate = Math.min(availabilities.size(), cars.size());
        for (int i = 0; i < shiftsToGenerate; ++i)
        {
            GenerateShiftRequest generateShiftRequest = new GenerateShiftRequest(date, type, cars.get(i).getId(), availabilities.get(i).getUser().getId());
            shifts.add(shiftRepository.save(shiftMapper.toShift(generateShiftRequest)));
        }
    }

    @Transactional
    public ShiftResponse start(StartShiftRequest startShiftRequest)
    {
        userRepository.findById(startShiftRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(startShiftRequest.getUserId()));
        Shift shift = shiftRepository.findByUser_IdAndDateAndType(startShiftRequest.getUserId(), startShiftRequest.getDate(), startShiftRequest.getType()).orElseThrow(ShiftExceptionSupplier.shiftStart(startShiftRequest.getUserId(), startShiftRequest.getDate(), startShiftRequest.getType()));

        if (shift.getIsStarted())
        {
            throw ShiftExceptionSupplier.shiftAlreadyStarted(shift.getId()).get();
        }

        if (shift.getIsDone())
        {
            throw ShiftExceptionSupplier.shiftAlreadyDone(shift.getId()).get();
        }

        if (!canShiftBeStartedToday(shift.getDate()))
        {
            throw ShiftExceptionSupplier.shiftCanNotBeStartedToday(shift.getId()).get();
        }

        shift = shiftMapper.toShift(shift, startShiftRequest);

        checkIfStartShiftDataAreCorrect(startShiftRequest, shift.getCar());

        List<Photo> photos = new ArrayList<>();

        List<String> startCarPhotosUlrs = startShiftRequest.getStartCarPhotosUrls();
        for (var startCarPhotoUrl : startCarPhotosUlrs)
        {
            photos.add(new Photo(startCarPhotoUrl, PhotoType.SHIFT_START, shift));
        }

        String startMileagePhotoUrl = startShiftRequest.getStartMileagePhotoUrl();
        photos.add(new Photo(startMileagePhotoUrl, PhotoType.SHIFT_START_MILEAGE, shift));

        photoRepository.saveAll(photos);

        return shiftMapper.toShiftResponse(shift);
    }

    private boolean canShiftBeStartedToday(LocalDate date)
    {
        return Objects.equals(date, LocalDate.now());
    }

    private void checkIfStartShiftDataAreCorrect(StartShiftRequest startShiftRequest, Car car)
    {
        List<String> startCarPhotosUrls = startShiftRequest.getStartCarPhotosUrls();
        checkIfCarPhotosUrlsAreCorrect(startCarPhotosUrls);

        Integer startMileage = startShiftRequest.getStartMileage();
        checkIfMileageIsCorrect(car.getMileage(), startMileage);

        String startMileagePhotoUrl = startShiftRequest.getStartMileagePhotoUrl();
        checkIfMileagePhotoUrlIsCorrect(startMileagePhotoUrl);
    }

    public Boolean isDone(Long id)
    {
        Shift shift = shiftRepository.findById(id).orElseThrow(ShiftExceptionSupplier.shiftNotFound(id));
        return shift.getIsDone();
    }

    @Transactional
    public ShiftResponse end(EndShiftRequest endShiftRequest)
    {
        Shift shift = shiftRepository.findById(endShiftRequest.getId()).orElseThrow(ShiftExceptionSupplier.shiftNotFound(endShiftRequest.getId()));

        if (!shift.getIsStarted())
        {
            throw ShiftExceptionSupplier.shiftNotStarted(shift.getId()).get();
        }

        if (shift.getIsDone())
        {
            throw ShiftExceptionSupplier.shiftAlreadyDone(shift.getId()).get();
        }

        checkIfEndShiftDataAreCorrect(endShiftRequest, shift);

        shift = shiftMapper.toShift(shift, endShiftRequest);

        List<Photo> photos = new ArrayList<>();

        List<String> endCarPhotosUlrs = endShiftRequest.getEndCarPhotosUrls();
        for (var endCarPhotoUrl : endCarPhotosUlrs)
        {
            photos.add(new Photo(endCarPhotoUrl, PhotoType.SHIFT_END, shift));
        }

        String endMileagePhotoUrl = endShiftRequest.getEndMileagePhotoUrl();
        photos.add(new Photo(endMileagePhotoUrl, PhotoType.SHIFT_END_MILEAGE, shift));

        String invoicePhotoUrl = endShiftRequest.getInvoicePhotoUrl();
        photos.add(new Photo(invoicePhotoUrl, PhotoType.INVOICE, shift));

        photoRepository.saveAll(photos);

        updateStatistics(shift);

        updateCarMileage(shift.getCar(), shift.getEndMileage());

        return shiftMapper.toShiftResponse(shift);
    }

    private void checkIfEndShiftDataAreCorrect(EndShiftRequest endShiftRequest, Shift shift)
    {
        List<String> endCarPhotosUrls = endShiftRequest.getEndCarPhotosUrls();
        checkIfCarPhotosUrlsAreCorrect(endCarPhotosUrls);

        Integer endMileage = endShiftRequest.getEndMileage();
        checkIfMileageIsCorrect(shift.getStartMileage(), endMileage);

        String endMileagePhotoUrl = endShiftRequest.getEndMileagePhotoUrl();
        checkIfMileagePhotoUrlIsCorrect(endMileagePhotoUrl);

        BigDecimal lpg = endShiftRequest.getLpg();
        checkIfLpgIsCorrect(lpg);

        BigDecimal petrol = endShiftRequest.getPetrol();
        checkIfPetrolIsCorrect(petrol);

        String invoicePhotoUrl = endShiftRequest.getInvoicePhotoUrl();
        checkIfInvoicePhotoUrlIsCorrect(invoicePhotoUrl);
    }

    private void checkIfCarPhotosUrlsAreCorrect(List<String> photosUrls)
    {
        if (!areEnoughCarPhotosFromOutside(photosUrls))
        {
            throw ShiftExceptionSupplier.notEnoughPhotosOfCarFromOutside().get();
        }

        for (var photo : photosUrls)
        {
            if (isPhotoEmpty(photo))
            {
                throw ShiftExceptionSupplier.notEnoughPhotosOfCarFromOutside().get();
            }
        }
    }

    private boolean areEnoughCarPhotosFromOutside(List<String> photosUrls)
    {
        return photosUrls.size() >= 8;
    }

    private void checkIfMileageIsCorrect(Integer oldMileage, Integer newMileage)
    {
        if (oldMileage == null || newMileage == null)
        {
            throw ShiftExceptionSupplier.emptyMileage().get();
        }

        if (isMileageIncorrect(oldMileage) || isMileageIncorrect(newMileage))
        {
            throw ShiftExceptionSupplier.incorrectMileage().get();
        }

        if (oldMileage > newMileage)
        {
            throw ShiftExceptionSupplier.incorrectMileage().get();
        }
    }

    private boolean isMileageIncorrect(Integer mileage)
    {
        return mileage < 0;
    }

    private void checkIfMileagePhotoUrlIsCorrect(String photoUrl)
    {
        if (isPhotoEmpty(photoUrl))
        {
            throw ShiftExceptionSupplier.emptyMileagePhoto().get();
        }
    }

    private void checkIfInvoicePhotoUrlIsCorrect(String photoUrl)
    {
        if (isPhotoEmpty(photoUrl))
        {
            throw ShiftExceptionSupplier.emptyInvoicePhoto().get();
        }
    }

    private boolean isPhotoEmpty(String photo)
    {
        return photo.isEmpty();
    }

    private void updateStatistics(Shift shift)
    {
        StatisticsUpdateRequest statisticsUpdateRequest = getStatisticsUpdateRequest(shift);

        Optional<Statistics> userMonthStatisticsCandidate = statisticsRepository.findByUserIdAndDate(shift.getUser().getId(), adjustDate(shift.getDate()));

        if (userMonthStatisticsCandidate.isEmpty())
        {
            StatisticsRequest statisticsRequest = getStatisticsRequest(shift);
            statisticsRepository.save(statisticsMapper.toStatistics(statisticsRequest));
        }
        else
        {
            Statistics userMonthStatistics = userMonthStatisticsCandidate.get();
            statisticsMapper.toStatistics(userMonthStatistics, statisticsUpdateRequest);
        }
    }

    private StatisticsUpdateRequest getStatisticsUpdateRequest(Shift shift)
    {
        if (!shift.getIsDone())
        {
            throw ShiftExceptionSupplier.shiftNotDone(shift.getId()).get();
        }
        return statisticsMapper.toStatisticsUpdateRequest(shift);
    }

    private LocalDate adjustDate(LocalDate date)
    {
        return date.withDayOfMonth(1);
    }

    private StatisticsRequest getStatisticsRequest(Shift shift)
    {
        if (!shift.getIsDone())
        {
            throw ShiftExceptionSupplier.shiftNotDone(shift.getId()).get();
        }
        return statisticsMapper.toStatisticsRequest(shift);
    }

    private void updateCarMileage(Car car, Integer kilometersTraveled)
    {
        car.setMileage(car.getMileage() + kilometersTraveled);
    }

    public List<ShiftResponse> findAllByDayAndType(LocalDate date, Type type)
    {
        return shiftRepository.findAllByDateAndType(date, type).stream().map(shiftMapper::toShiftResponse).collect(Collectors.toList());
    }

    public ExtendedShiftResponse getInfo(Long id)
    {
        Shift shift = shiftRepository.findById(id).orElseThrow(ShiftExceptionSupplier.shiftNotFound(id));
        if (!shift.getIsDone())
        {
            throw ShiftExceptionSupplier.shiftNotDone(id).get();
        }
        return shiftMapper.toExtendedShiftResponse(shift);
    }

    @Transactional
    public ShiftResponse updateFuel(UpdateFuelRequest updateFuelRequest)
    {
        Shift shift = shiftRepository.findById(updateFuelRequest.getId()).orElseThrow(ShiftExceptionSupplier.shiftNotFound(updateFuelRequest.getId()));
        if (!shift.getIsDone())
        {
            throw ShiftExceptionSupplier.shiftNotDone(updateFuelRequest.getId()).get();
        }
        checkIfUpdateFuelDataAreCorrect(updateFuelRequest);

        shift = shiftMapper.toShift(shift, updateFuelRequest);

        return shiftMapper.toShiftResponse(shift);
    }

    private void checkIfUpdateFuelDataAreCorrect(UpdateFuelRequest updateFuelRequest)
    {
        BigDecimal lpg = updateFuelRequest.getLpg();
        checkIfLpgIsCorrect(lpg);

        BigDecimal petrol = updateFuelRequest.getPetrol();
        checkIfPetrolIsCorrect(petrol);
    }

    private void checkIfLpgIsCorrect(BigDecimal lpg)
    {
        if (isFuelEmpty(lpg))
        {
            throw ShiftExceptionSupplier.emptyLpgConsumption().get();
        }

        if (isFuelIncorrect(lpg))
        {
            throw ShiftExceptionSupplier.incorrectLpgConsumption().get();
        }
    }

    private void checkIfPetrolIsCorrect(BigDecimal petrol)
    {
        if (isFuelEmpty(petrol))
        {
            throw ShiftExceptionSupplier.emptyPetrolConsumption().get();
        }

        if (isFuelIncorrect(petrol))
        {
            throw ShiftExceptionSupplier.incorrectPetrolConsumption().get();
        }
    }

    private boolean isFuelEmpty(BigDecimal fuel)
    {
        return fuel == null;
    }

    private boolean isFuelIncorrect(BigDecimal fuel)
    {
        return fuel.compareTo(BigDecimal.ZERO) <= 0;
    }
}
