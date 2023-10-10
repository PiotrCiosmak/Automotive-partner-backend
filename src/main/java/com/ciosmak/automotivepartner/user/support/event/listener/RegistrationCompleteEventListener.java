package com.ciosmak.automotivepartner.user.support.event.listener;

import com.ciosmak.automotivepartner.token.verification.api.request.VerificationTokenRequest;
import com.ciosmak.automotivepartner.token.verification.api.response.VerificationTokenResponse;
import com.ciosmak.automotivepartner.token.verification.service.VerificationTokenService;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.support.event.RegistrationCompleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent>
{
    private final VerificationTokenService verificationTokenService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event)
    {
        User user = event.getUser();

        VerificationTokenRequest verificationTokenRequest = generateNewVerificationToken(user);
        VerificationTokenResponse verificationTokenResponse = verificationTokenService.save(verificationTokenRequest);

        String url = event.getApplicationUrl() + "/api/tokens/verification/verify_email?token=" + verificationTokenResponse.getToken();

        //TODO wysyłać maila
        log.info("Click the link to complete your registration: {}", url);
    }

    private VerificationTokenRequest generateNewVerificationToken(User user)
    {
        return new VerificationTokenRequest(UUID.randomUUID().toString(), generateTokenExpirationTime(), user.getId());
    }

    private LocalDateTime generateTokenExpirationTime()
    {
        int default_expiration_time = 15;

        LocalDateTime now = LocalDateTime.now();
        return now.plusMinutes(default_expiration_time);
    }
}
