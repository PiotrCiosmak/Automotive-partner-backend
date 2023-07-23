package com.ciosmak.automotivepartner.shift.support;

import com.ciosmak.automotivepartner.shared.api.response.ErrorMessageResponse;
import com.ciosmak.automotivepartner.shift.support.exception.ShiftNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ShiftExceptionAdvisor
{
    private static final Logger LOG = LoggerFactory.getLogger(ShiftExceptionAdvisor.class);

    @ExceptionHandler(ShiftNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse shiftNotFound(ShiftNotFoundException exception)
    {
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getLocalizedMessage());
    }
}
