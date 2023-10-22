package com.ciosmak.automotivepartner.email.support;

import com.ciosmak.automotivepartner.shared.api.response.ErrorMessageResponse;
import com.ciosmak.automotivepartner.shared.exception.EmailTakenException;
import com.ciosmak.automotivepartner.shared.exception.EmptyEmailException;
import com.ciosmak.automotivepartner.shared.exception.IncorrectEmailException;
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
public class EmailExceptionAdvisor
{
    private static final Logger LOG = LoggerFactory.getLogger(EmailExceptionAdvisor.class);
    private final MessageSource messageSource;

    @ExceptionHandler(EmailTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessageResponse emailTaken(EmailTakenException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(EmptyEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse emptyEmail(EmptyEmailException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(IncorrectEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse incorrectEmail(IncorrectEmailException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }
}
