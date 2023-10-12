package com.ciosmak.automotivepartner.shared.event.listener;

import com.ciosmak.automotivepartner.shared.event.RegistrationCompleteEvent;
import com.ciosmak.automotivepartner.shared.utils.TokenExpirationTime;
import com.ciosmak.automotivepartner.token.verification.api.request.VerificationTokenRequest;
import com.ciosmak.automotivepartner.token.verification.api.response.VerificationTokenResponse;
import com.ciosmak.automotivepartner.token.verification.service.VerificationTokenService;
import com.ciosmak.automotivepartner.user.domain.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent>
{
    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event)
    {
        User user = event.getUser();

        VerificationTokenRequest verificationTokenRequest = generateNewVerificationToken(user);
        VerificationTokenResponse verificationTokenResponse = verificationTokenService.save(verificationTokenRequest);

        String url = event.getApplicationUrl() + "/api/tokens/verification/verify_email?token=" + verificationTokenResponse.getToken();

        //TODO zmienić te błedy na moje własne
        try
        {
            sendVerificationEmail(url, user);
        }
        catch (MessagingException | UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }

    private VerificationTokenRequest generateNewVerificationToken(User user)
    {
        return new VerificationTokenRequest(UUID.randomUUID().toString(), TokenExpirationTime.generateTokenExpirationTime(), user.getId());
    }


    private void sendVerificationEmail(String url, User user) throws MessagingException, UnsupportedEncodingException
    {
        String subject = "Weryfikacja adresu email";
        String senderName = "AutomotivePartner";
        String mailContent = "<p> Cześć " + user.getFirstName() + ", </p>" +
                "<p>Dziękujemy za rejestrację w naszym serwisie.</p>" +
                "<p>Proszę kliknąć poniższy link, aby ukończyć proces rejestracji.</p>" +
                "<a href=\"" + url + "\">Zweryfikuj swój adres e-mail, aby aktywować swoje konto.</a>" +
                "<p>Dziękujemy<br>AutomotivePartner";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("automotive.partner.biz@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
