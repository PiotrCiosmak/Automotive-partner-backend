/*
package com.ciosmak.automotivepartner.event.listener;

import com.ciosmak.automotivepartner.event.RegistrationCompleteEvent;
import com.ciosmak.automotivepartner.user.User;
import com.ciosmak.automotivepartner.user.UserService;
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
    private final UserService userService;

    private final JavaMailSender mailSender;
    private User user;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event)
    {
        user = event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        userService.saveUserVerificationToken(user, verificationToken);
        String url = event.getApplicationUrl() + "/register/verifyEmail?token=" + verificationToken;
        try
        {
            sendVerificationEmail(url);
        }
        catch (MessagingException | UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration : {}", url);
    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException
    {
        String subject = "Email Verification";
        String senderName = "Automotive Partner";
        String mailContent = "<p> Hi, " + user.getFirstName() + ", </p>" +
                "<p>Thank you for registering with us, Please, follow the link below to complete your registration.</p>" +
                "<a href=\"" + url + "\">Verify your email to activate your account</a>" +
                "<p>Thank you <br> Automotive Partner";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("piotrciosmak2001@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    public void sendPasswordResetVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException
    {
        String subject = "Password Reset Request Verification";
        String senderName = "Automotive Partner";
        String mailContent = "<p> Hi, " + user.getFirstName() + ", </p>" +
                "<p><b>You recently requested to rest your password, </b>" +
                "Please, follow the link below to complete the action.</p>"+
                "<a href=\"" + url + "\">Reset password</a>" +
                "<br> Automotive Partner";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("piotrciosmak2001@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
*/
