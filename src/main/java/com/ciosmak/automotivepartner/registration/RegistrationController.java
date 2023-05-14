package com.ciosmak.automotivepartner.registration;

import com.ciosmak.automotivepartner.event.RegistrationCompleteEvent;
import com.ciosmak.automotivepartner.event.listener.RegistrationCompleteEventListener;
import com.ciosmak.automotivepartner.registration.token.VerificationToken;
import com.ciosmak.automotivepartner.registration.token.VerificationTokenRepository;
import com.ciosmak.automotivepartner.user.User;
import com.ciosmak.automotivepartner.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController
{
    private final UserService userService;

    private final ApplicationEventPublisher publisher;

    private final VerificationTokenRepository tokenRepository;

    private final RegistrationCompleteEventListener eventListener;

    private final HttpServletRequest servletRequest;

    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request)
    {
        User user = userService.registerUser(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success! Please, check your email to complete your registration";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token)
    {
        String url = applicationUrl(servletRequest) + "/register/resend-verification-token?token=" + token;
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken.getUser().isEnabled())
        {
            return "This account has already been verified, please login";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid"))
        {
            return "Email verified successfully. Now you can login to your account";
        }
        return "Invalid verification link, <a href=\"" + url + "\"> Get a new verification link</a>";
    }

    @GetMapping("/resend-verification-token")
    public String resendVerificationToken(@RequestParam("token") String oldToken, final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException
    {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        //User user = verificationToken.getUser();
        resendVerificationTokenEmail(applicationUrl(request), verificationToken);
        return "A new verification link has been sent to your email, please, check to active your account";
    }

    private void resendVerificationTokenEmail(String applicationUrl, VerificationToken verificationToken) throws MessagingException, UnsupportedEncodingException
    {
        String url = applicationUrl + "/register/verifyEmail?token=" + verificationToken.getToken();
        eventListener.sendVerificationEmail(url);
        log.info("Click the link to verify your registration :  {}", url);
    }

    public String applicationUrl(HttpServletRequest request)
    {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
