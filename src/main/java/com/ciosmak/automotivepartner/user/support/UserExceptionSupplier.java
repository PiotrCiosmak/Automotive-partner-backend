package com.ciosmak.automotivepartner.user.support;

import com.ciosmak.automotivepartner.user.support.exception.*;

import java.util.function.Supplier;

public class UserExceptionSupplier
{
    public static Supplier<UserNotFoundException> userNotFound(Long id)
    {
        return () -> new UserNotFoundException(id);
    }

    public static Supplier<EmailTakenException> emailTaken()
    {
        return EmailTakenException::new;
    }

    public static Supplier<EmailNotInDatabaseException> emailNotInDatabase()
    {
        return EmailNotInDatabaseException::new;
    }

    public static Supplier<IncorrectUserDataException> incorrectUserData()
    {
        return IncorrectUserDataException::new;
    }

    public static Supplier<UserBlockedException> userBlocked(Long id)
    {
        return () -> new UserBlockedException(id);
    }

    public static Supplier<UserUnblockedException> userUnblocked(Long id)
    {
        return () -> new UserUnblockedException(id);
    }

    public static Supplier<UserAdminException> userAdmin(Long id)
    {
        return () -> new UserAdminException(id);
    }

    public static Supplier<UserNotAdminException> userNotAdmin(Long id)
    {
        return () -> new UserNotAdminException(id);
    }
}
