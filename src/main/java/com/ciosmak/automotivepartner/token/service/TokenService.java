package com.ciosmak.automotivepartner.token.service;

import com.ciosmak.automotivepartner.shared.event.RegistrationCompleteEvent;
import com.ciosmak.automotivepartner.shared.utils.UrlUtils;
import com.ciosmak.automotivepartner.token.api.request.TokenRequest;
import com.ciosmak.automotivepartner.token.api.response.TokenResponse;
import com.ciosmak.automotivepartner.token.domain.Token;
import com.ciosmak.automotivepartner.token.repository.TokenRepository;
import com.ciosmak.automotivepartner.token.support.TokenExceptionSupplier;
import com.ciosmak.automotivepartner.token.support.TokenMapper;
import com.ciosmak.automotivepartner.token.support.TokenType;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class TokenService
{
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final TokenMapper tokenMapper;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher publisher;


    public TokenResponse save(TokenRequest tokenRequest)
    {
        Token token = tokenRepository.save(tokenMapper.toToken(tokenRequest));
        return tokenMapper.toTokenResponse(token);
    }

    public String verifyEmail(String token, final HttpServletRequest request)
    {
        Token verificationToken = tokenRepository.findByTokenAndType(token, TokenType.VERIFICATION).orElseThrow(TokenExceptionSupplier.invalidToken());

        User user = verificationToken.getUser();

        if (!isValid(verificationToken))
        {
            tokenRepository.delete(verificationToken);
            publisher.publishEvent(new RegistrationCompleteEvent(user, UrlUtils.applicationUrl(request)));
            return "Ten link już wygasł. Nowy link aktywacyjny został wysłany na twój adres email.";
        }

        if (isUserEnabled(verificationToken))
        {
            return "To konto zostało już zweryfikowane, możesz się zalogować.";
        }

        userRepository.save(userMapper.toEnabledUser(user));

        return "Adres email został zweryfikowany poprawnie. Już możesz się zalogowac na swoje konto.";
    }

    public boolean isChangePasswordTokenValid(String token)
    {
        Token changePasswordToken = tokenRepository.findByTokenAndType(token, TokenType.CHANGE_PASSWORD).orElseThrow(TokenExceptionSupplier.invalidToken());

        if (!isValid(changePasswordToken))
        {
            tokenRepository.delete(changePasswordToken);
            return false;
        }
        return true;
    }

    private boolean isValid(Token token)
    {
        LocalDateTime now = LocalDateTime.now();
        return !token.getExpirationTime().isBefore(now);
    }

    private boolean isUserEnabled(Token token)
    {
        return token.getUser().getIsEnabled();
    }
}
