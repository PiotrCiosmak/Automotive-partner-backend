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

    public EmailResponse add(EmailRequest emailRequest)
    {
        String emailCandidate = emailRequest.getEmail();

        if (isEmailTaken(emailCandidate))
        {
            throw EmailExceptionSupplier.emailTaken().get();
        }

        if (isEmailInValid(emailCandidate))
        {
            throw EmailExceptionSupplier.inCorrectEmail().get();
        }

        Optional<User> user = userRepository.findByEmail(emailCandidate);

        if (user.isPresent())
        {
            throw UserExceptionSupplier.emailTaken().get();
        }

        Email email = emailRepository.save(emailMapper.toEmail(emailRequest));
        return emailMapper.toEmailResponse(email);
    }

    private Boolean isEmailTaken(String email)
    {
        return emailRepository.findByEmail(email).isPresent();
    }

    private Boolean isEmailInValid(String email)
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

    public void delete(EmailRequest emailRequest)
    {
        Email email = emailRepository.findByEmail(emailRequest.getEmail()).orElseThrow(EmailExceptionSupplier.emailNotFound());
        emailRepository.delete(email);
    }
}
