package com.ciosmak.automotivepartner.availability.support;

import com.ciosmak.automotivepartner.availability.support.exception.AvailabilityAlreadySubmittedException;
import com.ciosmak.automotivepartner.availability.support.exception.AvailabilitySubmittingTooLateException;
import com.ciosmak.automotivepartner.availability.support.exception.IncorrectAvailabilityDateException;
import com.ciosmak.automotivepartner.availability.support.exception.IncorrectAvailabilityTypesException;
import com.ciosmak.automotivepartner.shared.api.response.ErrorMessageResponse;
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
public class AvailabilityExceptionAdvisor
{
    private static final Logger LOG = LoggerFactory.getLogger(AvailabilityExceptionAdvisor.class);
    private final MessageSource messageSource;

    @ExceptionHandler(AvailabilityAlreadySubmittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse availabilityAlreadySubmitted(AvailabilityAlreadySubmittedException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(AvailabilitySubmittingTooLateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse availabilitySubmittingTooLate(AvailabilitySubmittingTooLateException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(IncorrectAvailabilityDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse incorrectAvailabilityDate(IncorrectAvailabilityDateException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(IncorrectAvailabilityTypesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse incorrectAvailabilityTypes(IncorrectAvailabilityTypesException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }
}
