package com.ciosmak.automotivepartner.api.repository;

import com.ciosmak.automotivepartner.email.domain.Email;
import com.ciosmak.automotivepartner.email.repository.EmailRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmailRepositoryTest
{
    @Autowired
    private EmailRepository emailRepository;

    @Test
    public void shouldReturnEmailWhenEmailIsInDatabase()
    {
        Email email = Email.builder().email("test@gmail.com").build();

        Email savedEmail = emailRepository.save(email);

        Assertions.assertThat(savedEmail).isNotNull();
        Assertions.assertThat(savedEmail.getId()).isGreaterThan(0);
    }

    @Test
    public void shouldFindEmailByEmailWhenEmailIsInDatabase()
    {
        Email email = Email.builder().email("test@gmail.com").build();

        emailRepository.save(email);

        Optional<Email> foundEmail = emailRepository.findByEmail(email.getEmail());

        Assertions.assertThat(foundEmail).isNotEmpty();
        Assertions.assertThat(foundEmail.get()).isNotNull();
    }

    @Test
    public void shouldDeleteEmailWhenEmailIsInDatabase()
    {
        Email email = Email.builder().email("test@gmail.com").build();

        emailRepository.save(email);

        emailRepository.deleteById(email.getId());
        Optional<Email> foundEmail = emailRepository.findById(email.getId());

        Assertions.assertThat(foundEmail).isEmpty();
    }
}
