package com.ciosmak.automotivepartner.user.support;

import com.ciosmak.automotivepartner.shared.exception.EmptyEmailException;
import com.ciosmak.automotivepartner.shared.exception.IncorrectEmailException;
import com.ciosmak.automotivepartner.shared.exception.InvalidTokenException;
import com.ciosmak.automotivepartner.user.support.exception.*;

import java.util.function.Supplier;

public class UserExceptionSupplier
{
    public static Supplier<ChangePasswordLinkSendException> changePasswordLinkSend()
    {
        return ChangePasswordLinkSendException::new;
    }

    public static Supplier<EmailTakenException> emailTaken(String email)
    {
        return () -> new EmailTakenException(email);
    }

    public static Supplier<EmptyEmailException> emptyEmail()
    {
        return EmptyEmailException::new;
    }

    public static Supplier<EmptyFirstNameException> emptyFirstName()
    {
        return EmptyFirstNameException::new;
    }

    public static Supplier<EmptyLastNameException> emptyLastName()
    {
        return EmptyLastNameException::new;
    }

    public static Supplier<EmptyPasswordException> emptyPassword()
    {
        return EmptyPasswordException::new;
    }

    public static Supplier<EmptyPhoneNumberException> emptyPhoneNumber()
    {
        return EmptyPhoneNumberException::new;
    }

    public static Supplier<IncorrectEmailException> incorrectEmail()
    {
        return IncorrectEmailException::new;
    }

    public static Supplier<IncorrectPasswordException> incorrectPassword()
    {
        return IncorrectPasswordException::new;
    }

    public static Supplier<IncorrectPhoneNumberException> incorrectPhoneNumber()
    {
        return IncorrectPhoneNumberException::new;
    }

    public static Supplier<SuperAdminDeleteException> superAdminDelete()
    {
        return SuperAdminDeleteException::new;
    }

    public static Supplier<SuperAdminDegradedToAdminException> superAdminDegradedToAdmin()
    {
        return SuperAdminDegradedToAdminException::new;
    }

    public static Supplier<SuperAdminDegradedToDriverException> superAdminDegradedToDriver()
    {
        return SuperAdminDegradedToDriverException::new;
    }

    public static Supplier<SuperAdminBlockedException> superAdminBlocked()
    {
        return SuperAdminBlockedException::new;
    }

    public static Supplier<TooShortPasswordException> tooShortPassword()
    {
        return TooShortPasswordException::new;
    }

    public static Supplier<UnapprovedEmailException> unapprovedEmail(String email)
    {
        return () -> new UnapprovedEmailException(email);
    }

    public static Supplier<UnverifiedAccountForgotPasswordException> unverifiedAccountForgotPassword()
    {
        return UnverifiedAccountForgotPasswordException::new;
    }

    public static Supplier<UserAlreadyAdminException> userAlreadyAdmin()
    {
        return UserAlreadyAdminException::new;
    }

    public static Supplier<UserAlreadyBlockedException> userAlreadyBlocked()
    {
        return UserAlreadyBlockedException::new;
    }

    public static Supplier<UserBlockedException> userBlocked()
    {
        return UserBlockedException::new;
    }

    public static Supplier<UserDisabledException> userDisabled()
    {
        return UserDisabledException::new;
    }

    public static Supplier<UserAlreadyDriverException> userAlreadyDriver()
    {
        return UserAlreadyDriverException::new;
    }

    public static Supplier<UserAlreadySuperAdminException> userAlreadySuperAdmin()
    {
        return UserAlreadySuperAdminException::new;
    }

    public static Supplier<UserNotBlockedException> userNotBlocked()
    {
        return UserNotBlockedException::new;
    }

    public static Supplier<UserNotFoundException> userNotFound(Long id)
    {
        return () -> new UserNotFoundException(id);
    }

    public static Supplier<WeakPasswordException> weakPassword()
    {
        return WeakPasswordException::new;
    }

    public static Supplier<InvalidTokenException> invalidToken()
    {
        return InvalidTokenException::new;
    }
}
