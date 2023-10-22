package com.ciosmak.automotivepartner.user.support;

import com.ciosmak.automotivepartner.shared.api.response.ErrorMessageResponse;
import com.ciosmak.automotivepartner.shared.exception.EmptyEmailException;
import com.ciosmak.automotivepartner.shared.exception.IncorrectEmailException;
import com.ciosmak.automotivepartner.shared.exception.InvalidTokenException;
import com.ciosmak.automotivepartner.user.support.exception.*;
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
public class UserExceptionAdvisor
{
    private static final Logger LOG = LoggerFactory.getLogger(UserExceptionAdvisor.class);
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

    @ExceptionHandler(EmptyFirstNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse emptyFirstName(EmptyFirstNameException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(EmptyLastNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse emptyLastName(EmptyLastNameException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(EmptyPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse emptyPassword(EmptyPasswordException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(EmptyPhoneNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse emptyPhoneNumber(EmptyPhoneNumberException exception)
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

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse incorrectPassword(IncorrectPasswordException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(IncorrectPhoneNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse incorrectPhoneNumber(IncorrectPhoneNumberException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(TooShortPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse tooShortPassword(TooShortPasswordException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(UnapprovedEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse unapprovedEmail(UnapprovedEmailException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(UserAlreadyAdminException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse userAdmin(UserAlreadyAdminException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(UserAlreadyBlockedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse userAlreadyBlocked(UserAlreadyBlockedException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(UserBlockedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse userBlocked(UserBlockedException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(UserAlreadyDriverException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse userAlreadyDriver(UserAlreadyDriverException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(UserNotBlockedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse userNotBlocked(UserNotBlockedException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(UserDisabledException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse userDisable(UserDisabledException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse userNotFound(UserNotFoundException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(WeakPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse weakPassword(WeakPasswordException exception)
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
}
