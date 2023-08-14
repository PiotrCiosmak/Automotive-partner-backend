package com.ciosmak.automotivepartner.availability.support;

import com.ciosmak.automotivepartner.availability.support.exception.AvailabilityAlreadySubmittedException;
import com.ciosmak.automotivepartner.availability.support.exception.IncorrectDateException;
import com.ciosmak.automotivepartner.shared.api.response.ErrorMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AvailabilityExceptionAdvisor
{
    private static final Logger LOG = LoggerFactory.getLogger(AvailabilityExceptionAdvisor.class);

    @ExceptionHandler(AvailabilityAlreadySubmittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse availabilityAlreadySubmitted(AvailabilityAlreadySubmittedException exception)
    {
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getLocalizedMessage());
    }

    @ExceptionHandler(IncorrectDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse IncorrectDate(IncorrectDateException exception)
    {
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getLocalizedMessage());
    }
}
