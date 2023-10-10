package com.ciosmak.automotivepartner.token.verification.service;

import com.ciosmak.automotivepartner.token.verification.api.request.VerificationTokenRequest;
import com.ciosmak.automotivepartner.token.verification.api.response.VerificationTokenResponse;
import com.ciosmak.automotivepartner.token.verification.domain.VerificationToken;
import com.ciosmak.automotivepartner.token.verification.repository.VerificationTokenRepository;
import com.ciosmak.automotivepartner.token.verification.support.VerificationTokenExceptionSupplier;
import com.ciosmak.automotivepartner.token.verification.support.VerificationTokenMapper;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class VerificationTokenService
{
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final VerificationTokenMapper verificationTokenMapper;
    private final UserMapper userMapper;

    public VerificationTokenResponse save(VerificationTokenRequest verificationTokenRequest)
    {
        VerificationToken verificationToken = verificationTokenRepository.save(verificationTokenMapper.toVerificationToken(verificationTokenRequest));
        return verificationTokenMapper.toVerificationTokenResponse(verificationToken);
    }

    public String verifyEmail(String token)
    {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(VerificationTokenExceptionSupplier.invalidVerificationToken());

        User user = verificationToken.getUser();

        if (!isValid(verificationToken))
        {
            verificationTokenRepository.delete(verificationToken);
            return "Link wygasł.";
        }

        userRepository.save(userMapper.toEnabledUser(user));

        if (isUserEnabled(verificationToken))
        {
            return "To konto zostało już zweryfikowane, możesz się zalogować.";
        }

        return "Adres email został zweryfikowany poprawnie. Już możesz się zalogowac na swoje konto.";

    }

    private boolean isValid(VerificationToken verificationToken)
    {
        LocalDateTime now = LocalDateTime.now();
        return !verificationToken.getExpirationTime().isBefore(now);
    }

    private boolean isUserEnabled(VerificationToken verificationToken)
    {
        return verificationToken.getUser().getIsEnabled();
    }
}
