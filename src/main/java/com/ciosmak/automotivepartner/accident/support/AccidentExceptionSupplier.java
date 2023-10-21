package com.ciosmak.automotivepartner.accident.support;

import com.ciosmak.automotivepartner.accident.support.exception.AccidentNotFoundException;
import com.ciosmak.automotivepartner.accident.support.exception.AccidentPhotosNotFoundException;
import com.ciosmak.automotivepartner.accident.support.exception.DocumentPhotosNotFoundException;
import com.ciosmak.automotivepartner.shared.exception.EmptyMileageException;
import com.ciosmak.automotivepartner.shared.exception.EmptyMileagePhotoException;
import com.ciosmak.automotivepartner.shared.exception.IncorrectMileageException;

import java.util.function.Supplier;

public class AccidentExceptionSupplier
{
    public static Supplier<AccidentNotFoundException> accidentNotFound(Long id)
    {
        return () -> new AccidentNotFoundException(id);
    }

    public static Supplier<AccidentPhotosNotFoundException> accidentPhotosNotFound()
    {
        return AccidentPhotosNotFoundException::new;
    }

    public static Supplier<DocumentPhotosNotFoundException> documentPhotosNotFound()
    {
        return DocumentPhotosNotFoundException::new;
    }

    public static Supplier<EmptyMileagePhotoException> emptyMileagePhoto()
    {
        return EmptyMileagePhotoException::new;
    }

    public static Supplier<EmptyMileageException> emptyMileage()
    {
        return EmptyMileageException::new;
    }

    public static Supplier<IncorrectMileageException> incorrectMileage()
    {
        return IncorrectMileageException::new;
    }
}
