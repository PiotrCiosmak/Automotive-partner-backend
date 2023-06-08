package com.ciosmak.automotivepartner.email.support;

import com.ciosmak.automotivepartner.email.api.request.EmailRequest;
import com.ciosmak.automotivepartner.email.api.response.EmailResponse;
import com.ciosmak.automotivepartner.email.domain.Email;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper
{
    public Email toEmail(EmailRequest emailRequest)
    {
        return new Email(emailRequest.getEmail());
    }

    public Email toEmail(Email email, EmailRequest emailRequest)
    {
        email.setEmail(emailRequest.getEmail());
        return email;
    }

    public EmailResponse toEmailResponse(Email email)
    {
        return new EmailResponse(email.getId(), email.getEmail());
    }
}
