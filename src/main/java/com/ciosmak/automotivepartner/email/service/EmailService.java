package com.ciosmak.automotivepartner.email.service;

import com.ciosmak.automotivepartner.email.api.request.EmailRequest;
import com.ciosmak.automotivepartner.email.api.response.EmailResponse;
import com.ciosmak.automotivepartner.email.domain.Email;
import com.ciosmak.automotivepartner.email.repository.EmailRepository;
import com.ciosmak.automotivepartner.email.support.EmailExceptionSupplier;
import com.ciosmak.automotivepartner.email.support.EmailMapper;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class EmailService
{
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;

    @Transactional
    public EmailResponse add(EmailRequest emailRequest)
    {
        String emailCandidate = emailRequest.getEmail();

        checkIfEmailIsCorrect(emailCandidate);

        Optional<User> user = userRepository.findByEmail(emailCandidate);

        if (user.isPresent())
        {
            throw UserExceptionSupplier.emailTaken(user.get().getEmail()).get();
        }

        Email email = emailRepository.save(emailMapper.toEmail(emailRequest));
        return emailMapper.toEmailResponse(email);
    }

    private void checkIfEmailIsCorrect(String email)
    {
        if (isEmailTaken(email))
        {
            throw EmailExceptionSupplier.emailTaken(email).get();
        }

        if (isEmailEmpty(email))
        {
            throw EmailExceptionSupplier.emptyEmail().get();
        }

        if (isEmailIncorrect(email))
        {
            throw EmailExceptionSupplier.incorrectEmail().get();
        }
    }

    private Boolean isEmailTaken(String email)
    {
        return emailRepository.findByEmail(email).isPresent();
    }


    private Boolean isEmailIncorrect(String email)
    {
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    @Transactional
    public void delete(EmailRequest emailRequest)
    {
        String emailCandidate = emailRequest.getEmail();

        if (isEmailEmpty(emailCandidate))
        {
            throw EmailExceptionSupplier.emptyEmail().get();
        }

        Email email = emailRepository.findByEmail(emailCandidate).orElseThrow(EmailExceptionSupplier.incorrectEmail());
        emailRepository.delete(email);
    }

    private Boolean isEmailEmpty(String email)
    {
        return email.isEmpty();
    }
}
