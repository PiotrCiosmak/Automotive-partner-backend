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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final MessageSource messageSource;

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
        String subject = messageSource.getMessage("VerificationEmailSubject", null, LocaleContextHolder.getLocale());
        String senderName = messageSource.getMessage("EmailSenderName", null, LocaleContextHolder.getLocale());
        String mailContent = messageSource.getMessage("VerificationEmailContent", new Object[]{user.getFirstName(), url}, LocaleContextHolder.getLocale());

        emailMessage(subject, senderName, mailContent, user);
    }

    public void sendChangePasswordEmail(String url, User user) throws MessagingException, UnsupportedEncodingException
    {
        String subject = messageSource.getMessage("ChangePasswordEmailSubject", null, LocaleContextHolder.getLocale());
        String senderName = messageSource.getMessage("EmailSenderName", null, LocaleContextHolder.getLocale());
        String mailContent = messageSource.getMessage("ChangePasswordEmailContent", new Object[]{user.getFirstName(), url}, LocaleContextHolder.getLocale());

        emailMessage(subject, senderName, mailContent, user);
    }

    private void emailMessage(String subject, String senderName,
                              String mailContent, User user)
            throws MessagingException, UnsupportedEncodingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(messageSource.getMessage("Email", null, LocaleContextHolder.getLocale()), senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
