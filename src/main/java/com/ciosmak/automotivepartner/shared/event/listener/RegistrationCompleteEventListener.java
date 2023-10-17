package com.ciosmak.automotivepartner.shared.event.listener;

import com.ciosmak.automotivepartner.shared.event.RegistrationCompleteEvent;
import com.ciosmak.automotivepartner.token.api.request.TokenRequest;
import com.ciosmak.automotivepartner.token.api.response.TokenResponse;
import com.ciosmak.automotivepartner.token.service.TokenService;
import com.ciosmak.automotivepartner.token.support.TokenUtils;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent>
{
    private final TokenService tokenService;
    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event)
    {
        User user = event.getUser();

        TokenRequest tokenRequest = TokenUtils.generateNewVerificationToken(user);
        TokenResponse tokenResponse = tokenService.save(tokenRequest);

        String url = event.getApplicationUrl() + "/api/tokens/verify-email?token=" + tokenResponse.getToken();

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

    private void sendVerificationEmail(String url, User user) throws MessagingException, UnsupportedEncodingException
    {
        String subject = "Weryfikacja adresu email";
        String senderName = "AutomotivePartner";
        String mailContent = "<p> Cześć " + user.getFirstName() + ", </p>" +
                "<p>Dziękujemy za rejestrację w naszym serwisie.</p>" +
                "<p>Proszę kliknąć poniższy link, aby ukończyć proces rejestracji.</p>" +
                "<a href=\"" + url + "\">Zweryfikuj swój adres e-mail, aby aktywować swoje konto.</a>" +
                "<p>Dziękujemy!<br>AutomotivePartner";
        emailMessage(subject, senderName, mailContent, user);
    }

    public void sendChangePasswordEmail(String url, User user) throws MessagingException, UnsupportedEncodingException
    {
        String subject = "Zmiana hasła";
        String senderName = "AutomotivePartner";
        String mailContent = "<p> Cześć " + user.getFirstName() + ", </p>" +
                "<p>Otrzymaliśmy Twoją prośbę o zmianę hasła.<p>" + "" +
                "<p>Proszę kliknij poniższy link, aby zmienić hasło.</p>" +
                "<a href=\"" + url + "\">Zmień hasło</a>" +
                "<p>Jeśli prośba o zmianę hasła nie została zgłoszona przez Ciebie, możesz bezpiecznie zignorować tę wiadomość e-mail. Ktoś inny mógł przez pomyłkę wpisać Twój adres e-mail.</p>" +
                "<p>Dziękujemy!<br>AutomotivePartner";

        emailMessage(subject, senderName, mailContent, user);
    }

    private void emailMessage(String subject, String senderName,
                              String mailContent, User user)
            throws MessagingException, UnsupportedEncodingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("automotive.partner.biz@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
