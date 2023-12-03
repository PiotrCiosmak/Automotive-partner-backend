package com.ciosmak.automotivepartner.repository;

import com.ciosmak.automotivepartner.token.domain.Token;
import com.ciosmak.automotivepartner.token.repository.TokenRepository;
import com.ciosmak.automotivepartner.token.support.TokenType;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.support.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TokenRepositoryTest
{
    private static final LocalDateTime tokenExpirationTime = LocalDateTime.now().plusMinutes(15);

    @Autowired
    private TokenRepository tokenRepository;

    private User user;

    private Token verificationToken;

    private Token changePasswordToken;

    @BeforeEach
    public void setUp()
    {
        user = User.builder().firstName("Test").lastName("Test").email("test@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).build();

        verificationToken = Token.builder().token("test123").type(TokenType.VERIFICATION).expirationTime(tokenExpirationTime).user(user).build();
        tokenRepository.save(verificationToken);

        changePasswordToken = Token.builder().token("test321").type(TokenType.CHANGE_PASSWORD).expirationTime(tokenExpirationTime).user(user).build();
        tokenRepository.save(changePasswordToken);
    }

    @Test
    public void shouldSaveVerificationToken()
    {
        Token savedToken = tokenRepository.save(verificationToken);

        Assertions.assertThat(savedToken).isNotNull();
        Assertions.assertThat(savedToken.getToken()).isEqualTo("test123");
        Assertions.assertThat(savedToken.getType()).isEqualTo(TokenType.VERIFICATION);
        Assertions.assertThat(savedToken.getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @Test
    public void shouldSaveChangePasswordToken()
    {
        Token savedToken = tokenRepository.save(changePasswordToken);

        Assertions.assertThat(savedToken).isNotNull();
        Assertions.assertThat(savedToken.getToken()).isEqualTo("test321");
        Assertions.assertThat(savedToken.getType()).isEqualTo(TokenType.CHANGE_PASSWORD);
        Assertions.assertThat(savedToken.getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @ParameterizedTest
    @CsvSource({"VERIFICATION, test123", "CHANGE_PASSWORD, test321"})
    public void shouldFindTokenByTokenContentAndTypeWhenTokenOfThatTypeAndContentIsInDatabase(TokenType tokenType, String tokenContent)
    {
        Optional<Token> foundToken = tokenRepository.findByTokenAndType(tokenContent, tokenType);

        Assertions.assertThat(foundToken).isNotEmpty();
        Assertions.assertThat(foundToken.get()).isNotNull();
        Assertions.assertThat(foundToken.get().getToken()).isEqualTo(tokenContent);
        Assertions.assertThat(foundToken.get().getType()).isEqualTo(tokenType);
        Assertions.assertThat(foundToken.get().getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @ParameterizedTest
    @CsvSource({"VERIFICATION, abcd123", "CHANGE_PASSWORD, abcd123", "VERIFICATION, test321", "CHANGE_PASSWORD, test123",})
    public void shouldNotFindTokenByTokenContentAndTypeWhenTokenOfThatTypeAndContentIsInNotDatabase(TokenType tokenType, String tokenContent)
    {
        Optional<Token> foundToken = tokenRepository.findByTokenAndType(tokenContent, tokenType);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({"VERIFICATION, test123", "CHANGE_PASSWORD, test321"})
    public void shouldFindTokenByUserAndTypeAndExpirationTimeAfterWhenTokenOfThatTypeAndExpirationTimeAfterConnectedToThisUserIsInDatabase(TokenType tokenType, String tokenContent)
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(user, tokenType, LocalDateTime.MAX);

        Assertions.assertThat(foundToken).isNotEmpty();
        Assertions.assertThat(foundToken.get()).isNotNull();
        Assertions.assertThat(foundToken.get().getToken()).isEqualTo(tokenContent);
        Assertions.assertThat(foundToken.get().getType()).isEqualTo(tokenType);
        Assertions.assertThat(foundToken.get().getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @ParameterizedTest
    @ValueSource(strings = {"VERIFICATION", "CHANGE_PASSWORD"})
    public void shouldNotFindTokenByUserAndTypeAndExpirationTimeAfterWhenTokenOfThatTypeAndExpirationTimeAfterConnectedToThisUserIsNotInDatabase(TokenType tokenType)
    {
        tokenRepository.deleteAll();

        loadExpiredTokens();

        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(user, tokenType, LocalDateTime.now());

        Assertions.assertThat(foundToken).isEmpty();
    }

    private void loadExpiredTokens()
    {
        verificationToken = Token.builder().token("abcde123").type(TokenType.VERIFICATION).expirationTime(LocalDateTime.now().minusDays(1)).user(user).build();
        tokenRepository.save(verificationToken);

        changePasswordToken = Token.builder().token("abcde321").type(TokenType.CHANGE_PASSWORD).expirationTime(LocalDateTime.now().minusDays(1)).user(user).build();
        tokenRepository.save(changePasswordToken);
    }

    @ParameterizedTest
    @ValueSource(strings = {"VERIFICATION", "CHANGE_PASSWORD"})
    public void shouldNotFindTokenByUserAndTypeAndExpirationTimeAfterWhenUserIsWrong(TokenType tokenType)
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(null, tokenType, LocalDateTime.MAX);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"VERIFICATION", "CHANGE_PASSWORD"})
    public void shouldNotFindTokenByUserAndTypeAndExpirationTimeAfterWhenExpirationTimeAfterIsWrong(TokenType tokenType)
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(user, tokenType, LocalDateTime.MIN);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({"VERIFICATION, test123", "CHANGE_PASSWORD, test321"})
    public void shouldFindTokenByUserAndTypeAndExpirationTimeBeforeWhenTokenOfThatTypeAndExpirationTimeBeforeConnectedToThisUserIsInDatabase(TokenType tokenType, String tokenContent)
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(user, tokenType, LocalDateTime.MIN);

        Assertions.assertThat(foundToken).isNotEmpty();
        Assertions.assertThat(foundToken.get()).isNotNull();
        Assertions.assertThat(foundToken.get().getToken()).isEqualTo(tokenContent);
        Assertions.assertThat(foundToken.get().getType()).isEqualTo(tokenType);
        Assertions.assertThat(foundToken.get().getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @ParameterizedTest
    @ValueSource(strings = {"VERIFICATION", "CHANGE_PASSWORD"})
    public void shouldNotFindTokenByUserAndTypeAndExpirationTimeBeforeWhenTokenOfThatTypeAndExpirationTimeBeforeConnectedToThisUserIsNotInDatabase(TokenType tokenType)
    {
        tokenRepository.deleteAll();

        loadUnExpiredTokens();

        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(user, tokenType, LocalDateTime.now());

        Assertions.assertThat(foundToken).isEmpty();
    }

    private void loadUnExpiredTokens()
    {
        verificationToken = Token.builder().token("abcde123").type(TokenType.VERIFICATION).expirationTime(LocalDateTime.now().plusDays(1)).user(user).build();
        tokenRepository.save(verificationToken);

        changePasswordToken = Token.builder().token("abcde321").type(TokenType.CHANGE_PASSWORD).expirationTime(LocalDateTime.now().plusDays(1)).user(user).build();
        tokenRepository.save(changePasswordToken);
    }

    @ParameterizedTest
    @ValueSource(strings = {"VERIFICATION", "CHANGE_PASSWORD"})
    public void shouldNotFindTokenByUserAndTypeAndExpirationTimeBeforeWhenUserIsWrong(TokenType tokenType)
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(null, tokenType, LocalDateTime.MIN);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"VERIFICATION", "CHANGE_PASSWORD"})
    public void shouldNotFindTokenByUserAndTypeAndExpirationTimeBeforeWhenExpirationTimeBeforeIsWrong(TokenType tokenType)
    {
        tokenRepository.save(verificationToken);
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(user, tokenType, LocalDateTime.MAX);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldDeleteVerificationTokenWhenTokenWithThisIdIsInDatabase()
    {
        tokenRepository.deleteById(verificationToken.getId());

        Optional<Token> foundToken = tokenRepository.findById(verificationToken.getId());

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldDeleteChangePasswordTokenWhenTokenWithThisIdIsInDatabase()
    {
        tokenRepository.deleteById(changePasswordToken.getId());

        Optional<Token> foundToken = tokenRepository.findById(changePasswordToken.getId());

        Assertions.assertThat(foundToken).isEmpty();
    }
}
