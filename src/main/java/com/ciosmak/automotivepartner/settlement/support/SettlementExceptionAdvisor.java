package com.ciosmak.automotivepartner.settlement.support;

import com.ciosmak.automotivepartner.settlement.support.exception.*;
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
public class SettlementExceptionAdvisor
{
    private static final Logger LOG = LoggerFactory.getLogger(SettlementExceptionSupplier.class);
    private final MessageSource messageSource;

    @ExceptionHandler(BugAlreadyReportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse bugAlreadyReported(BugAlreadyReportedException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(EmptyNetAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse emptyNetAmount(EmptyNetAmountException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(EmptyTipAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse emptyTipAmount(EmptyTipAmountException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(IncorrectNetAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse incorrectNetAmount(IncorrectNetAmountException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(IncorrectOptionalFactorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse incorrectOptionalFactor(IncorrectOptionalFactorException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(IncorrectOptionalPenaltyAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse incorrectOptionalPenaltyAmount(IncorrectOptionalPenaltyAmountException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(IncorrectTipAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse incorrectTipAmount(IncorrectTipAmountException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(SettlementAlreadyCompletedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse settlementAlreadyCompleted(SettlementAlreadyCompletedException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }


    @ExceptionHandler(SettlementIncompleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessageResponse settlementIncomplete(SettlementIncompleteException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }

    @ExceptionHandler(SettlementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse SettlementNotFound(SettlementNotFoundException exception)
    {
        String errorMessage = messageSource.getMessage(getClassName(exception), exception.properties, LocaleContextHolder.getLocale());
        LOG.error(errorMessage);
        return new ErrorMessageResponse(errorMessage);
    }
}
