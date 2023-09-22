package com.ciosmak.automotivepartner.accident.support;

import com.ciosmak.automotivepartner.accident.support.exception.AccidentNotFoundException;
import com.ciosmak.automotivepartner.accident.support.exception.AccidentPhotosNotFoundException;
import com.ciosmak.automotivepartner.accident.support.exception.DocumentPhotosNotFoundException;
import com.ciosmak.automotivepartner.shared.api.response.ErrorMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AccidentExceptionAdvisor
{
    private static final Logger LOG = LoggerFactory.getLogger(AccidentExceptionAdvisor.class);

    @ExceptionHandler(AccidentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse accidentNotFound(AccidentNotFoundException exception)
    {
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getLocalizedMessage());
    }

    @ExceptionHandler(AccidentPhotosNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse accidentPhotosNotFound(AccidentPhotosNotFoundException exception)
    {
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getLocalizedMessage());
    }

    @ExceptionHandler(DocumentPhotosNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse documentPhotosNotFound(DocumentPhotosNotFoundException exception)
    {
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getLocalizedMessage());
    }
}
