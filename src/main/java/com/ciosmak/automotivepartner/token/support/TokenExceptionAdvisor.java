package com.ciosmak.automotivepartner.token.support;

import com.ciosmak.automotivepartner.shared.api.response.ErrorMessageResponse;
import com.ciosmak.automotivepartner.shared.exception.InvalidTokenException;
import com.ciosmak.automotivepartner.token.support.exception.ExpiredChangePasswordTokenException;
import com.ciosmak.automotivepartner.token.support.exception.NotExpiredChangePasswordTokenException;
import com.ciosmak.automotivepartner.user.support.UserExceptionAdvisor;
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
public class TokenExceptionAdvisor
{
    private static final Logger LOG = LoggerFactory.getLogger(UserExceptionAdvisor.class);
    private final MessageSource messageSource;

    @ExceptionHandler(ExpiredChangePasswordTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse expiredChangePasswordToken(ExpiredChangePasswordTokenException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse invalidToken(InvalidTokenException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(NotExpiredChangePasswordTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse notExpiredChangePasswordToken(NotExpiredChangePasswordTokenException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }
}
