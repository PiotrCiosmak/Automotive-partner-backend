package com.ciosmak.automotivepartner.email.service;

import com.ciosmak.automotivepartner.email.api.request.EmailRequest;
import com.ciosmak.automotivepartner.email.api.response.EmailResponse;
import com.ciosmak.automotivepartner.email.domain.Email;
import com.ciosmak.automotivepartner.email.repository.EmailRepository;
import com.ciosmak.automotivepartner.email.support.EmailExceptionSupplier;
import com.ciosmak.automotivepartner.email.support.EmailMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailService
{
    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;

    public EmailResponse add(EmailRequest emailRequest)
    {
        String emailCandidate = emailRequest.getEmail();
        if (isEmailTaken(emailCandidate))
        {
            throw EmailExceptionSupplier.emailTakenException().get();
        }

        if (isEmailInValid(emailCandidate))
        {
            throw EmailExceptionSupplier.incorrectEmail().get();
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
        return !email.contains("@.");
    }
}
