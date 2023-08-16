package com.ciosmak.automotivepartner.accident.support;

import com.ciosmak.automotivepartner.accident.support.exception.*;
import com.ciosmak.automotivepartner.shared.exception.IncorrectMileageException;

import java.util.function.Supplier;

public class AccidentExceptionSupplier
{
    public static Supplier<AccidentPhotosNotFoundException> accidentPhotosNotFound()
    {
        return AccidentPhotosNotFoundException::new;
    }

    public static Supplier<DocumentPhotosNotFoundException> documentPhotosNotFound()
    {
        return DocumentPhotosNotFoundException::new;
    }

    public static Supplier<IncorrectMileageException> incorrectMileage()
    {
        return IncorrectMileageException::new;
    }

    public static Supplier<MileagePhotoNotFoundException> mileagePhotoNotFound()
    {
        return MileagePhotoNotFoundException::new;
    }

    public static Supplier<AccidentAllReadyReportedException> accidentAllReadyReported(Long id)
    {
        return () -> new AccidentAllReadyReportedException(id);
    }

    public static Supplier<AccidentNotFoundException> accidentNotFound(Long id)
    {
        return () -> new AccidentNotFoundException(id);
    }
}
