package com.ciosmak.automotivepartner.user;

import com.ciosmak.automotivepartner.registration.RegistrationRequest;
import com.ciosmak.automotivepartner.registration.token.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface IUserService
{
    List<User> getUsers();

    User registerUser(RegistrationRequest registrationRequest);

    Optional<User> findByEmail(String email);

    void saveUserVerificationToken(User user, String token);

    String validateToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    void createPasswordResetTokenForUser(User user, String passwordToken);

    String validatePasswordResetToken(String passwordResetToken);

    User findUserByPasswordToken(String passwordResetToken);

    void resetUserPassword(User user, String newPassword);
}
