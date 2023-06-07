/*
package com.ciosmak.automotivepartner.registration.password;

import com.ciosmak.automotivepartner.registration.token.VerificationToken;
import com.ciosmak.automotivepartner.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PasswordResetTokenService
{
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public void createPasswordResetTokenForUser(User user, String passwordToken)
    {
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    public String validatePasswordResetToken(String token)
    {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null)
        {
            return "Invalid password reset token";
        }
        User user = passwordResetToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0)
        {
            return "link already expired, resend link";
        }
        return "valid";
    }

    public Optional<User> findUserByPasswordToken(String passwordToken)
    {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordToken).getUser());
    }
}
*/
