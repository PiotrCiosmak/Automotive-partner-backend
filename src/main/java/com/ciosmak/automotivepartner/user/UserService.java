package com.ciosmak.automotivepartner.user;

import com.ciosmak.automotivepartner.exception.UserAlreadyExistsException;
import com.ciosmak.automotivepartner.registration.RegistrationRequest;
import com.ciosmak.automotivepartner.registration.token.VerificationToken;
import com.ciosmak.automotivepartner.registration.token.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;

    @Override
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest registrationRequest)
    {
        Optional<User> user = this.findByEmail(registrationRequest.email());
        if (user.isPresent())
        {
            throw new UserAlreadyExistsException("User with email " + registrationRequest.email() + " already exists");
        }
        var newUser = new User();
        newUser.setFirstName(registrationRequest.firstName());
        newUser.setLastName(registrationRequest.lastName());
        newUser.setEmail(registrationRequest.email());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.password()));
        newUser.setRole(registrationRequest.role());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(User user, String token)
    {
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String token)
    {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null)
        {
            return "Invalid verification token";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0)
        {
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }


    @Override
    public VerificationToken generateNewVerificationToken(String oldToken)
    {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        var tokenExpirationTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(tokenExpirationTime.getTokenExpirationTime());
        return tokenRepository.save(verificationToken);
    }
}
