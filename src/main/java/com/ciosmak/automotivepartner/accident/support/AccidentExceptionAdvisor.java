package com.ciosmak.automotivepartner.accident.support;

import com.ciosmak.automotivepartner.accident.support.exception.AccidentNotFoundException;
import com.ciosmak.automotivepartner.accident.support.exception.AccidentPhotosNotFoundException;
import com.ciosmak.automotivepartner.accident.support.exception.DocumentPhotosNotFoundException;
import com.ciosmak.automotivepartner.shared.api.response.ErrorMessageResponse;
import com.ciosmak.automotivepartner.shared.exception.EmptyMileageException;
import com.ciosmak.automotivepartner.shared.exception.EmptyMileagePhotoException;
import com.ciosmak.automotivepartner.shared.exception.IncorrectMileageException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.ciosmak.automotivepartner.shared.utils.Utils.getClassName;

@ControllerAdvice
@AllArgsConstructor
public class AccidentExceptionAdvisor
{
    private static final Logger LOG = LoggerFactory.getLogger(AccidentExceptionAdvisor.class);
    private final MessageSource messageSource;

    @ExceptionHandler(AccidentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse accidentNotFound(AccidentNotFoundException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(AccidentPhotosNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse accidentPhotosNotFound(AccidentPhotosNotFoundException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(DocumentPhotosNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse documentPhotosNotFound(DocumentPhotosNotFoundException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(EmptyMileagePhotoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse emptyMileagePhoto(EmptyMileagePhotoException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(EmptyMileageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse emptyMileage(EmptyMileageException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(IncorrectMileageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse incorrectMileage(IncorrectMileageException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }
}
