package com.ciosmak.automotivepartner.token.verification.support;

import com.ciosmak.automotivepartner.token.verification.api.request.VerificationTokenRequest;
import com.ciosmak.automotivepartner.token.verification.api.response.VerificationTokenResponse;
import com.ciosmak.automotivepartner.token.verification.domain.VerificationToken;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class VerificationTokenMapper
{
    private final UserRepository userRepository;

    public VerificationToken toVerificationToken(VerificationTokenRequest verificationTokenRequest)
    {
        return new VerificationToken(verificationTokenRequest.getToken(), verificationTokenRequest.getExpirationTime(), userRepository.findById(verificationTokenRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(verificationTokenRequest.getUserId())));
    }

    public VerificationTokenResponse toVerificationTokenResponse(VerificationToken verificationToken)
    {
        return new VerificationTokenResponse(verificationToken.getId(), verificationToken.getToken(), verificationToken.getExpirationTime(), verificationToken.getUser().getId());
    }
}
