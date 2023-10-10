package com.ciosmak.automotivepartner.token.verification.support;

import com.ciosmak.automotivepartner.shared.api.response.ErrorMessageResponse;
import com.ciosmak.automotivepartner.token.verification.support.exception.InvalidVerificationTokenException;
import com.ciosmak.automotivepartner.user.support.UserExceptionAdvisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice

public class VerificationTokenExceptionAdvisor
{
    private static final Logger LOG = LoggerFactory.getLogger(UserExceptionAdvisor.class);

    @ExceptionHandler(InvalidVerificationTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse invalidVerificationToken(InvalidVerificationTokenException exception)
    {
        LOG.error(exception.getMessage(), exception);
        return new ErrorMessageResponse(exception.getMessage());
    }
}
