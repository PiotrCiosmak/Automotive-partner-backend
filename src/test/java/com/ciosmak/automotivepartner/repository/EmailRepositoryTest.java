package com.ciosmak.automotivepartner.repository;

import com.ciosmak.automotivepartner.email.domain.Email;
import com.ciosmak.automotivepartner.email.repository.EmailRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

    private static Email email;

    @BeforeAll
    public static void setUp()
    {
        email = Email.builder().email("test@example.com").build();
    }

    @Test
    public void shouldReturnEmailWhenEmailIsInDatabase()
    {
        Email savedEmail = emailRepository.save(email);

        Assertions.assertThat(savedEmail).isNotNull();
        Assertions.assertThat(savedEmail.getId()).isGreaterThan(0);
    }

    @Test
    public void shouldFindEmailByEmailWhenEmailIsInDatabase()
    {
        emailRepository.save(email);

        Optional<Email> foundEmail = emailRepository.findByEmail(email.getEmail());

        Assertions.assertThat(foundEmail).isNotEmpty();
        Assertions.assertThat(foundEmail.get()).isNotNull();
        Assertions.assertThat(foundEmail.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void shouldNotFindEmailByEmailWhenEmailIsNotInDatabase()
    {
        Optional<Email> foundEmail = emailRepository.findByEmail(email.getEmail());

        Assertions.assertThat(foundEmail).isEmpty();
    }

    @Test
    public void shouldDeleteEmailWhenEmailIsInDatabase()
    {
        emailRepository.save(email);

        emailRepository.deleteById(email.getId());
        Optional<Email> foundEmail = emailRepository.findById(email.getId());

        Assertions.assertThat(foundEmail).isEmpty();
    }
}
