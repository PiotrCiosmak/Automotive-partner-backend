package com.ciosmak.automotivepartner.repository;

import com.ciosmak.automotivepartner.email.domain.Email;
import com.ciosmak.automotivepartner.email.repository.EmailRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmailRepositoryTest
{
    private static int numberOfEmails;
    @Autowired
    private EmailRepository emailRepository;

    @BeforeEach
    public void setUp()
    {
        List<Email> emails = new ArrayList<>();
        emails.add(Email.builder().email("test.a@example.com").build());
        emailRepository.saveAll(emails);
        numberOfEmails = emails.size();
    }

    @Test
    public void shouldReturnEmailWhenEmailIsInDatabase()
    {
        Email savedEmail = emailRepository.save(Email.builder().email("test.b@example.com").build());

        Assertions.assertThat(savedEmail).isNotNull();
        Assertions.assertThat(savedEmail.getEmail()).isEqualTo("test.b@example.com");
        Assertions.assertThat(savedEmail.getId()).isGreaterThan(0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test.a@example.com"})
    public void shouldFindEmailByEmailWhenEmailIsInDatabase(String expectedEmail)
    {
        Optional<Email> foundEmail = emailRepository.findByEmail(expectedEmail);

        Assertions.assertThat(foundEmail).isNotEmpty();
        Assertions.assertThat(foundEmail.get()).isNotNull();
        Assertions.assertThat(foundEmail.get().getEmail()).isEqualTo(expectedEmail);
    }

    @Test
    public void shouldNotFindEmailByEmailWhenEmailIsNotInDatabase()
    {
        Optional<Email> foundEmail = emailRepository.findByEmail("nonexistsing@example.com");

        Assertions.assertThat(foundEmail).isEmpty();
    }

    @Test
    public void shouldDeleteEmailWhenEmailIsInDatabase()
    {
        long firstId = emailRepository.findAll().get(0).getId();
        long lastId = firstId + numberOfEmails;
        for (long i = firstId; i < lastId; ++i)
        {
            emailRepository.deleteById(i);
            Optional<Email> foundEmail = emailRepository.findById(i);

            Assertions.assertThat(foundEmail).isEmpty();
        }
    }
}
