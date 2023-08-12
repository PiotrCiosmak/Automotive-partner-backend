package com.ciosmak.automotivepartner.statistic.support;

import com.ciosmak.automotivepartner.shared.api.response.ErrorMessageResponse;
import com.ciosmak.automotivepartner.statistic.support.exception.IncorrectDateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StatisticsExceptionAdvisor
{
    private static final Logger LOG = LoggerFactory.getLogger(StatisticsExceptionAdvisor.class);

    @ExceptionHandler(IncorrectDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse incorrectDate(IncorrectDateException exception)
    {
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getLocalizedMessage());
    }
}
