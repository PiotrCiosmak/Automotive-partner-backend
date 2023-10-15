package com.ciosmak.automotivepartner.token.support;

import com.ciosmak.automotivepartner.token.api.request.TokenRequest;
import com.ciosmak.automotivepartner.token.api.response.TokenResponse;
import com.ciosmak.automotivepartner.token.domain.Token;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TokenMapper
{
    private final UserRepository userRepository;

    public Token toToken(TokenRequest tokenRequest)
    {
        return new Token(tokenRequest.getToken(), tokenRequest.getType(), tokenRequest.getExpirationTime(), userRepository.findById(tokenRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(tokenRequest.getUserId())));
    }

    public TokenResponse toTokenResponse(Token token)
    {
        return new TokenResponse(token.getId(), token.getToken(), token.getType(), token.getExpirationTime(), token.getUser().getId());
    }
}
