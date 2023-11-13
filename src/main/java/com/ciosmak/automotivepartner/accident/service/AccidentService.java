package com.ciosmak.automotivepartner.accident.service;

import com.ciosmak.automotivepartner.accident.api.request.AccidentRequest;
import com.ciosmak.automotivepartner.accident.api.request.LateAccidentRequest;
import com.ciosmak.automotivepartner.accident.api.resposne.AccidentResponse;
import com.ciosmak.automotivepartner.accident.api.resposne.BaseAccidentResponse;
import com.ciosmak.automotivepartner.accident.api.resposne.ExtendedAccidentResponse;
import com.ciosmak.automotivepartner.accident.domain.Accident;
import com.ciosmak.automotivepartner.accident.repository.AccidentRepository;
import com.ciosmak.automotivepartner.accident.support.AccidentExceptionSupplier;
import com.ciosmak.automotivepartner.accident.support.AccidentMapper;
import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.photo.domain.Photo;
import com.ciosmak.automotivepartner.photo.repository.PhotoRepository;
import com.ciosmak.automotivepartner.photo.support.PhotoType;
import com.ciosmak.automotivepartner.shift.domain.Shift;
import com.ciosmak.automotivepartner.shift.repository.ShiftRepository;
import com.ciosmak.automotivepartner.shift.support.ShiftExceptionSupplier;
import com.ciosmak.automotivepartner.shift.support.ShiftMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AccidentService
{
    private final AccidentRepository accidentRepository;
    private final ShiftRepository shiftRepository;
    private final PhotoRepository photoRepository;
    private final AccidentMapper accidentMapper;
    private final ShiftMapper shiftMapper;

    @Transactional
    public AccidentResponse report(AccidentRequest accidentRequest)
    {
        Shift shift = shiftRepository.findById(accidentRequest.getShiftId()).orElseThrow(ShiftExceptionSupplier.shiftNotFound(accidentRequest.getShiftId()));
        checkShift(shift);

        checkIfAccidentDataAreCorrect(accidentRequest, shift.getStartMileage());

        List<Photo> photos = new ArrayList<>();
        List<String> accidentPhotosUlrs = accidentRequest.getAccidentPhotosUrls();
        for (var accidentPhotoUlr : accidentPhotosUlrs)
        {
            photos.add(new Photo(accidentPhotoUlr, PhotoType.CAR_DAMAGE, shift));
        }

        List<String> documentPhotosUlrs = accidentRequest.getDocumentPhotoUrl();
        for (var documentPhotoUlr : documentPhotosUlrs)
        {
            photos.add(new Photo(documentPhotoUlr, PhotoType.STATEMENT, shift));
        }

        if (accidentRequest.getIsEndOfWork())
        {
            shift = shiftMapper.toShift(shift, accidentRequest);
            photos.add(new Photo(accidentRequest.getMileagePhotoUrl().get(), PhotoType.SHIFT_END_MILEAGE, shift));
            updateCarMileage(shift.getCar(), shift.getEndMileage());
        }

        photoRepository.saveAll(photos);

        Accident accident = accidentRepository.save(accidentMapper.toAccident(accidentRequest));
        return accidentMapper.toAccidentResponse(accident);
    }

    private void checkShift(Shift shift)
    {
        if (!shift.getIsStarted())
        {
            throw ShiftExceptionSupplier.shiftNotStarted(shift.getId()).get();
        }

        if (shift.getIsDone())
        {
            throw ShiftExceptionSupplier.shiftAlreadyDone(shift.getId()).get();
        }
    }

    private void checkIfAccidentDataAreCorrect(AccidentRequest accidentRequest, Integer startMileage)
    {
        List<String> accidentPhotosUrls = accidentRequest.getAccidentPhotosUrls();
        checkIfAccidentPhotosUrlsAreCorrect(accidentPhotosUrls);

        List<String> documentPhotosUrls = accidentRequest.getDocumentPhotoUrl();
        checkIfDocumentPhotosUrlsAreCorrect(documentPhotosUrls);

        if (accidentRequest.getIsEndOfWork())
        {
            accidentRequest.getMileagePhotoUrl().orElseThrow(AccidentExceptionSupplier.emptyMileagePhoto());
            Integer mileage = accidentRequest.getMileage().orElseThrow(AccidentExceptionSupplier.emptyMileage());
            checkIfMileageIsCorrect(startMileage, mileage);
        }
    }

    private void checkIfMileageIsCorrect(Integer oldMileage, Integer newMileage)
    {
        if (oldMileage == null || newMileage == null)
        {
            throw AccidentExceptionSupplier.emptyMileage().get();
        }

        if (isMileageIncorrect(oldMileage) || isMileageIncorrect(newMileage))
        {
            throw AccidentExceptionSupplier.incorrectMileage().get();
        }

        if (oldMileage > newMileage)
        {
            throw AccidentExceptionSupplier.incorrectMileage().get();
        }
    }

    private boolean isMileageIncorrect(Integer mileage)
    {
        return mileage < 0;
    }

    private void updateCarMileage(Car car, Integer endMileage)
    {
        car.setMileage(endMileage);
    }

    @Transactional
    public AccidentResponse completeUnreported(LateAccidentRequest lateAccidentRequest)
    {
        Shift shift = shiftRepository.findById(lateAccidentRequest.getShiftId()).orElseThrow(ShiftExceptionSupplier.shiftNotFound(lateAccidentRequest.getShiftId()));
        checkDoneShift(shift);

        checkIfLateAccidentDataAreCorrect(lateAccidentRequest, shift.getStartMileage());

        List<Photo> photos = new ArrayList<>();
        List<String> accidentPhotosUlrs = lateAccidentRequest.getAccidentPhotosUrls();
        for (var accidentPhotoUlr : accidentPhotosUlrs)
        {
            photos.add(new Photo(accidentPhotoUlr, PhotoType.CAR_DAMAGE, shift));
        }

        List<String> documentPhotosUlrs = lateAccidentRequest.getDocumentPhotoUrl();
        for (var documentPhotoUlr : documentPhotosUlrs)
        {
            photos.add(new Photo(documentPhotoUlr, PhotoType.STATEMENT, shift));
        }

        photoRepository.saveAll(photos);

        Accident accident = accidentRepository.save(accidentMapper.toAccident(lateAccidentRequest));
        return accidentMapper.toAccidentResponse(accident);
    }

    private void checkDoneShift(Shift shift)
    {
        if (!shift.getIsStarted())
        {
            throw ShiftExceptionSupplier.shiftNotStarted(shift.getId()).get();
        }

        if (!shift.getIsDone())
        {
            throw ShiftExceptionSupplier.shiftNotDone(shift.getId()).get();
        }
    }


    private void checkIfLateAccidentDataAreCorrect(LateAccidentRequest lateAccidentRequest, Integer startMileage)
    {
        List<String> accidentPhotosUrls = lateAccidentRequest.getAccidentPhotosUrls();
        checkIfAccidentPhotosUrlsAreCorrect(accidentPhotosUrls);

        List<String> documentPhotosUrls = lateAccidentRequest.getDocumentPhotoUrl();
        checkIfDocumentPhotosUrlsAreCorrect(documentPhotosUrls);
    }

    private void checkIfAccidentPhotosUrlsAreCorrect(List<String> photosUrls)
    {
        if (!areEnoughAccidentPhotos(photosUrls))
        {
            throw AccidentExceptionSupplier.accidentPhotosNotFound().get();
        }

        for (var photoUrl : photosUrls)
        {
            if (isPhotoUrlEmpty(photoUrl))
            {
                throw AccidentExceptionSupplier.accidentPhotosNotFound().get();
            }
        }
    }

    private boolean areEnoughAccidentPhotos(List<String> photosUrls)
    {
        return photosUrls.size() >= 4;
    }

    private void checkIfDocumentPhotosUrlsAreCorrect(List<String> photosUrls)
    {
        if (!areEnoughDocumentPhotos(photosUrls))
        {
            throw AccidentExceptionSupplier.documentPhotosNotFound().get();
        }

        for (var photoUrl : photosUrls)
        {
            if (isPhotoUrlEmpty(photoUrl))
            {
                throw AccidentExceptionSupplier.documentPhotosNotFound().get();
            }
        }
    }

    private boolean areEnoughDocumentPhotos(List<String> photosUrls)
    {
        return !photosUrls.isEmpty();
    }

    private boolean isPhotoUrlEmpty(String photoUrl)
    {
        return photoUrl.isEmpty();
    }

    public List<BaseAccidentResponse> findAllAccidents()
    {
        return accidentRepository.findAll().stream().map(accidentMapper::toBaseAccidentResponse).collect(Collectors.toList());
    }

    public ExtendedAccidentResponse findAccident(Long id)
    {
        Accident accident = accidentRepository.findById(id).orElseThrow(AccidentExceptionSupplier.accidentNotFound(id));
        return accidentMapper.toAdvanceAccidentResponse(accident);
    }
}
