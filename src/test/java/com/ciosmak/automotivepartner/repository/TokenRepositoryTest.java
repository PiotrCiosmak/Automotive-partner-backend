package com.ciosmak.automotivepartner.repository;

import com.ciosmak.automotivepartner.token.domain.Token;
import com.ciosmak.automotivepartner.token.repository.TokenRepository;
import com.ciosmak.automotivepartner.token.support.TokenType;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.support.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    public void shouldReturnVerificationTokenWhenTokenIsInDatabase()
    {
        Token savedToken = tokenRepository.save(verificationToken);

        Assertions.assertThat(savedToken).isNotNull();
        Assertions.assertThat(savedToken.getToken()).isEqualTo("test123");
        Assertions.assertThat(savedToken.getType()).isEqualTo(TokenType.VERIFICATION);
        Assertions.assertThat(savedToken.getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @Test
    public void shouldReturnChangePasswordTokenWhenTokenIsInDatabase()
    {
        Token savedToken = tokenRepository.save(changePasswordToken);

        Assertions.assertThat(savedToken).isNotNull();
        Assertions.assertThat(savedToken.getToken()).isEqualTo("test321");
        Assertions.assertThat(savedToken.getType()).isEqualTo(TokenType.CHANGE_PASSWORD);
        Assertions.assertThat(savedToken.getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @Test
    public void shouldFindVerificationTokenByTokenAndTypeWhenTokenIsInDatabase()
    {
        Optional<Token> foundToken = tokenRepository.findByTokenAndType("test123", TokenType.VERIFICATION);

        Assertions.assertThat(foundToken).isNotEmpty();
        Assertions.assertThat(foundToken.get()).isNotNull();
        Assertions.assertThat(foundToken.get().getToken()).isEqualTo("test123");
        Assertions.assertThat(foundToken.get().getType()).isEqualTo(TokenType.VERIFICATION);
        Assertions.assertThat(foundToken.get().getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @Test
    public void shouldFindChangePasswordTokenByTokenAndTypeWhenTokenIsInDatabase()
    {
        Optional<Token> foundToken = tokenRepository.findByTokenAndType("test321", TokenType.CHANGE_PASSWORD);

        Assertions.assertThat(foundToken).isNotEmpty();
        Assertions.assertThat(foundToken.get()).isNotNull();
        Assertions.assertThat(foundToken.get().getToken()).isEqualTo("test321");
        Assertions.assertThat(foundToken.get().getType()).isEqualTo(TokenType.CHANGE_PASSWORD);
        Assertions.assertThat(foundToken.get().getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @Test
    public void shouldNotFindVerificationTokenByTokenAndTypeWhenTokenIsNotInDatabase()
    {
        Optional<Token> foundToken = tokenRepository.findByTokenAndType("abcd123", TokenType.VERIFICATION);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindChangePasswordTokenByTokenAndTypeWhenTokenIsNotInDatabase()
    {
        Optional<Token> foundToken = tokenRepository.findByTokenAndType("abcd123", TokenType.CHANGE_PASSWORD);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindVerificationTokenByTokenAndTypeWhenTokenTypeIsWrong()
    {
        Optional<Token> foundToken = tokenRepository.findByTokenAndType("test123", TokenType.CHANGE_PASSWORD);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindChangePasswordTokenByTokenAndTypeWhenTokenTypeIsWrong()
    {
        Optional<Token> foundToken = tokenRepository.findByTokenAndType("test321", TokenType.VERIFICATION);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindVerificationTokenByTokenAndTypeWhenTokenIsWrong()
    {
        Optional<Token> foundToken = tokenRepository.findByTokenAndType("abcd123", TokenType.VERIFICATION);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindChangePasswordTokenByTokenAndTypeWhenTokenIsWrong()
    {
        Optional<Token> foundToken = tokenRepository.findByTokenAndType("abcd123", TokenType.CHANGE_PASSWORD);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldFindVerificationTokenByUserAndTypeAndExpirationTimeAfterWhenTokenIsInDatabase()
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(user, TokenType.VERIFICATION, LocalDateTime.MAX);

        Assertions.assertThat(foundToken).isNotEmpty();
        Assertions.assertThat(foundToken.get()).isNotNull();
        Assertions.assertThat(foundToken.get().getToken()).isEqualTo("test123");
        Assertions.assertThat(foundToken.get().getType()).isEqualTo(TokenType.VERIFICATION);
        Assertions.assertThat(foundToken.get().getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @Test
    public void shouldFindChangePasswordTokenByUserAndTypeAndExpirationTimeAfterWhenTokenIsInDatabase()
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(user, TokenType.CHANGE_PASSWORD, LocalDateTime.MAX);

        Assertions.assertThat(foundToken).isNotEmpty();
        Assertions.assertThat(foundToken.get()).isNotNull();
        Assertions.assertThat(foundToken.get().getToken()).isEqualTo("test321");
        Assertions.assertThat(foundToken.get().getType()).isEqualTo(TokenType.CHANGE_PASSWORD);
        Assertions.assertThat(foundToken.get().getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @Test
    public void shouldNotFindVerificationTokenByUserAndTypeAndExpirationTimeAfterWhenTypeIsWrong()
    {
        tokenRepository.delete(changePasswordToken);

        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(user, TokenType.CHANGE_PASSWORD, LocalDateTime.MAX);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindChangePasswordTokenByUserAndTypeAndExpirationTimeAfterWhenTypeIsWrong()
    {
        tokenRepository.delete(verificationToken);

        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(user, TokenType.VERIFICATION, LocalDateTime.MAX);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindVerificationTokenByUserAndTypeAndExpirationTimeAfterWhenUserIsWrong()
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(null, TokenType.CHANGE_PASSWORD, LocalDateTime.MAX);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindChangePasswordTokenByUserAndTypeAndExpirationTimeAfterWhenUserIsWrong()
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(null, TokenType.CHANGE_PASSWORD, LocalDateTime.MAX);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindVerificationTokenByUserAndTypeAndExpirationTimeAfterWhenExpirationTimeAfterIsWrong()
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(user, TokenType.VERIFICATION, LocalDateTime.MIN);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindChangePasswordTokenByUserAndTypeAndExpirationTimeAfterWhenExpirationTimeAfterIsWrong()
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(user, TokenType.CHANGE_PASSWORD, LocalDateTime.MIN);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldFindVerificationTokenByUserAndTypeAndExpirationTimeBeforeWhenTokenIsInDatabase()
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(user, TokenType.VERIFICATION, LocalDateTime.MIN);

        Assertions.assertThat(foundToken).isNotEmpty();
        Assertions.assertThat(foundToken.get()).isNotNull();
        Assertions.assertThat(foundToken.get().getToken()).isEqualTo("test123");
        Assertions.assertThat(foundToken.get().getType()).isEqualTo(TokenType.VERIFICATION);
        Assertions.assertThat(foundToken.get().getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @Test
    public void shouldFindChangePasswordTokenByUserAndTypeAndExpirationTimeBeforeWhenTokenIsInDatabase()
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(user, TokenType.CHANGE_PASSWORD, LocalDateTime.MIN);

        Assertions.assertThat(foundToken).isNotEmpty();
        Assertions.assertThat(foundToken.get()).isNotNull();
        Assertions.assertThat(foundToken.get().getToken()).isEqualTo("test321");
        Assertions.assertThat(foundToken.get().getType()).isEqualTo(TokenType.CHANGE_PASSWORD);
        Assertions.assertThat(foundToken.get().getExpirationTime()).isEqualTo(tokenExpirationTime);
    }

    @Test
    public void shouldNotFindVerificationTokenByUserAndTypeAndExpirationTimeBeforeWhenTypeIsWrong()
    {
        tokenRepository.deleteAll();

        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(user, TokenType.CHANGE_PASSWORD, LocalDateTime.MIN);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindChangePasswordTokenByUserAndTypeAndExpirationTimeBeforeWhenTypeIsWrong()
    {
        tokenRepository.deleteAll();

        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(user, TokenType.VERIFICATION, LocalDateTime.MIN);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindVerificationTokenByUserAndTypeAndExpirationTimeBeforeWhenUserIsWrong()
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(null, TokenType.VERIFICATION, LocalDateTime.MIN);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindChangePasswordTokenByUserAndTypeAndExpirationTimeBeforeWhenUserIsWrong()
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(null, TokenType.CHANGE_PASSWORD, LocalDateTime.MIN);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindVerificationTokenByUserAndTypeAndExpirationTimeBeforeWhenExpirationTimeBeforeIsWrong()
    {
        tokenRepository.save(verificationToken);
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(user, TokenType.VERIFICATION, LocalDateTime.MAX);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldNotFindChangePasswordTokenByUserAndTypeAndExpirationTimeExpirationTimeBeforeWhenTokenIsWrong()
    {
        Optional<Token> foundToken = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(user, TokenType.CHANGE_PASSWORD, LocalDateTime.MAX);

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldDeleteVerificationTokenWhenTokenIsInDatabase()
    {
        tokenRepository.deleteById(verificationToken.getId());

        Optional<Token> foundToken = tokenRepository.findById(verificationToken.getId());

        Assertions.assertThat(foundToken).isEmpty();
    }

    @Test
    public void shouldDeleteChangePasswordTokenWhenTokenIsInDatabase()
    {
        tokenRepository.deleteById(changePasswordToken.getId());

        Optional<Token> foundToken = tokenRepository.findById(changePasswordToken.getId());
        
        Assertions.assertThat(foundToken).isEmpty();
    }
}
