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
            throw UserExceptionSupplier.emailTaken().get();
        }

        Email email = emailRepository.save(emailMapper.toEmail(emailRequest));
        return emailMapper.toEmailResponse(email);
    }

    private void checkIfEmailIsCorrect(String email)
    {
        if (isEmailTaken(email))
        {
            throw EmailExceptionSupplier.emailTaken().get();
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

    private Boolean isEmailEmpty(String email)
    {
        return email.isEmpty();
    }

    private Boolean isEmailIncorrect(String email)
    {
        if (!email.contains("@"))
        {
            return true;
        }

        int indexOfAt = email.indexOf("@");
        int lastIndexOfDot = email.lastIndexOf(".");
        if (lastIndexOfDot == -1 || lastIndexOfDot < indexOfAt)
        {
            return true;
        }

        return email.endsWith(".");
    }

    @Transactional
    public void delete(EmailRequest emailRequest)
    {
        Email email = emailRepository.findByEmail(emailRequest.getEmail()).orElseThrow(EmailExceptionSupplier.incorrectEmail());
        emailRepository.delete(email);
    }
}
