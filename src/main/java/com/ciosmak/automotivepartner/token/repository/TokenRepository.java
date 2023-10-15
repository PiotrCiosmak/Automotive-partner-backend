package com.ciosmak.automotivepartner.token.repository;

import com.ciosmak.automotivepartner.token.domain.Token;
import com.ciosmak.automotivepartner.token.support.TokenType;
import com.ciosmak.automotivepartner.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>
{
    Optional<Token> findByTokenAndType(String token, TokenType type);

    Optional<Token> findByUserAndTypeAndExpirationTimeAfter(User user, TokenType type, LocalDateTime expirationTime);

    Optional<Token> findByUserAndTypeAndExpirationTimeBefore(User user, TokenType type, LocalDateTime expirationTime);
}
