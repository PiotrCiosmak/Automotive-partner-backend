package com.ciosmak.automotivepartner.user.service;

import com.ciosmak.automotivepartner.email.api.request.EmailRequest;
import com.ciosmak.automotivepartner.email.repository.EmailRepository;
import com.ciosmak.automotivepartner.email.service.EmailService;
import com.ciosmak.automotivepartner.shared.event.RegistrationCompleteEvent;
import com.ciosmak.automotivepartner.shared.event.listener.RegistrationCompleteEventListener;
import com.ciosmak.automotivepartner.shared.utils.Utils;
import com.ciosmak.automotivepartner.token.api.request.TokenRequest;
import com.ciosmak.automotivepartner.token.domain.Token;
import com.ciosmak.automotivepartner.token.repository.TokenRepository;
import com.ciosmak.automotivepartner.token.service.TokenService;
import com.ciosmak.automotivepartner.token.support.TokenExceptionSupplier;
import com.ciosmak.automotivepartner.token.support.TokenType;
import com.ciosmak.automotivepartner.token.support.TokenUtils;
import com.ciosmak.automotivepartner.user.api.request.UserChangePasswordRequest;
import com.ciosmak.automotivepartner.user.api.request.UserLoginDataRequest;
import com.ciosmak.automotivepartner.user.api.request.UserRequest;
import com.ciosmak.automotivepartner.user.api.response.UserResponse;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.Role;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import com.ciosmak.automotivepartner.user.support.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService
{
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final EmailRepository emailRepository;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationCompleteEventListener registrationCompleteEventListener;
    private final MessageSource messageSource;

    @Transactional
    public UserResponse register(UserRequest userRequest, final HttpServletRequest request)
    {
        checkIfUserDataAreCorrect(userRequest);

        User user = userRepository.save(userMapper.toUser(userRequest));

        EmailRequest emailRequest = new EmailRequest(userRequest.getEmail());
        emailService.delete(emailRequest);

        publisher.publishEvent(new RegistrationCompleteEvent(user, Utils.applicationUrl(request)));

        return userMapper.toUserResponse(user);
    }

    private void checkIfUserDataAreCorrect(UserRequest userRequest)
    {
        if (userRequest.getFirstName().isEmpty())
        {
            throw UserExceptionSupplier.emptyFirstName().get();
        }

        if (userRequest.getLastName().isEmpty())
        {
            throw UserExceptionSupplier.emptyLastName().get();
        }

        String phoneNumberCandidate = userRequest.getPhoneNumber();
        if (phoneNumberCandidate.isEmpty())
        {
            throw UserExceptionSupplier.emptyPhoneNumber().get();
        }
        if (isPhoneNumberIncorrect(phoneNumberCandidate))
        {
            throw UserExceptionSupplier.incorrectPhoneNumber().get();
        }

        String emailCandidate = userRequest.getEmail();
        if (emailCandidate.isEmpty())
        {
            throw UserExceptionSupplier.emptyEmail().get();
        }
        if (isEmailIncorrect(emailCandidate))
        {
            throw UserExceptionSupplier.incorrectEmail().get();
        }
        if (isEmailTaken(emailCandidate))
        {
            throw UserExceptionSupplier.emailTaken(emailCandidate).get();
        }
        if (isEmailUnapproved(emailCandidate))
        {
            throw UserExceptionSupplier.unapprovedEmail(emailCandidate).get();
        }

        String passwordCandidate = userRequest.getPassword();
        checkIfPasswordIsCorrect(passwordCandidate);
    }

    private void checkIfPasswordIsCorrect(String password)
    {
        if (password.isEmpty())
        {
            throw UserExceptionSupplier.emptyPassword().get();
        }
        if (isPasswordTooShort(password))
        {
            throw UserExceptionSupplier.tooShortPassword().get();
        }
        if (isPasswordWeak(password))
        {
            throw UserExceptionSupplier.weakPassword().get();
        }
    }

    private Boolean isPhoneNumberIncorrect(String phoneNumber)
    {
        Pattern phonePattern = Pattern.compile("(\\+\\d{1,2})?(\\d{9}|\\d{11,12})");
        Matcher phoneMatcher = phonePattern.matcher(phoneNumber);

        return !phoneMatcher.matches();
    }

    private Boolean isEmailIncorrect(String email)
    {
        if (!email.contains("@"))
        {
            return true;
        }

        int indexOfAt = email.indexOf("@");
        int lastIndexOfDot = email.lastIndexOf(".");
        if (lastIndexOfDot == -1 || lastIndexOfDot < indexOfAt)
        {
            return true;
        }

        return email.endsWith(".");
    }

    private Boolean isEmailTaken(String email)
    {
        return userRepository.findByEmail(email).isPresent();
    }

    private Boolean isEmailUnapproved(String email)
    {
        return emailRepository.findByEmail(email).isEmpty();
    }

    private Boolean isPasswordTooShort(String password)
    {
        return password.length() < 8;
    }

    private Boolean isPasswordWeak(String password)
    {
        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        Pattern letterPattern = Pattern.compile("[a-zA-Z]");
        Matcher letterMatcher = letterPattern.matcher(password);
        if (letterMatcher.find())
        {
            hasLetter = true;
        }

        Pattern digitPattern = Pattern.compile("\\d");
        Matcher digitMatcher = digitPattern.matcher(password);
        if (digitMatcher.find())
        {
            hasDigit = true;
        }

        Pattern specialCharPattern = Pattern.compile("[~!@#$%^&*()_+{}\\[\\]:;,.<>/?-]");
        Matcher specialCharMatcher = specialCharPattern.matcher(password);
        if (specialCharMatcher.find())
        {
            hasSpecialChar = true;
        }

        return !(hasLetter && hasDigit && hasSpecialChar);
    }

    public UserResponse login(UserLoginDataRequest userLoginDataRequest)
    {
        User user = userRepository.findByEmail(userLoginDataRequest.getEmail()).orElseThrow(UserExceptionSupplier.incorrectEmail());

        boolean isPasswordCorrect = isPasswordCorrect(userLoginDataRequest.getPassword(), user);
        if (!isPasswordCorrect)
        {
            throw UserExceptionSupplier.incorrectPassword().get();
        }

        boolean isEnabled = user.getIsEnabled();
        if (!isEnabled)
        {
            throw UserExceptionSupplier.userDisabled().get();
        }

        boolean isBlocked = user.getIsBlocked();
        if (isBlocked)
        {
            throw UserExceptionSupplier.userBlocked().get();
        }

        return userMapper.toUserResponse(user);
    }

    private boolean isPasswordCorrect(String password, User user)
    {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public String forgotPassword(String emailRequest, final HttpServletRequest request)
    {
        if (emailRequest.isEmpty())
        {
            throw UserExceptionSupplier.emptyEmail().get();
        }

        User user = userRepository.findByEmail(emailRequest).orElseThrow(UserExceptionSupplier.incorrectEmail());

        checkIfTokenExists(user);
        checkIfUserIsEnabled(user);

        TokenRequest changePasswordTokenRequest = TokenUtils.generateNewChangePasswordToken(user);
        tokenService.save(changePasswordTokenRequest);


        String url = Utils.applicationUrl(request) + "/api/users/change-password?token=" + changePasswordTokenRequest.getToken();
        registrationCompleteEventListener.sendChangePasswordEmail(url, user);
        return messageSource.getMessage("ChangePasswordLinkAlreadySent", null, LocaleContextHolder.getLocale());

    }

    private void checkIfTokenExists(User user)
    {
        checkIfValidTokenExists(user);
        deleteInvalidTokenIfExists(user);
    }

    private void checkIfValidTokenExists(User user)
    {
        Optional<Token> token = tokenRepository.findByUserAndTypeAndExpirationTimeAfter(user, TokenType.CHANGE_PASSWORD, LocalDateTime.now());
        if (token.isPresent())
        {
            throw TokenExceptionSupplier.notExpiredChangePasswordToken().get();
        }
    }

    private void deleteInvalidTokenIfExists(User user)
    {
        Optional<Token> token = tokenRepository.findByUserAndTypeAndExpirationTimeBefore(user, TokenType.CHANGE_PASSWORD, LocalDateTime.now());
        token.ifPresent(tokenRepository::delete);
    }

    private void checkIfUserIsEnabled(User user)
    {
        if (!user.getIsEnabled())
        {
            throw UserExceptionSupplier.unverifiedAccountForgotPassword().get();
        }
    }

    @Transactional
    public String changePassword(UserChangePasswordRequest userChangePasswordRequest)
    {
        String changePasswordToken = userChangePasswordRequest.getToken();
        boolean isValid = tokenService.isChangePasswordTokenValid(changePasswordToken);
        if (!isValid)
        {
            throw TokenExceptionSupplier.expiredChangePasswordToken().get();
        }
        String password = userChangePasswordRequest.getPassword();
        checkIfPasswordIsCorrect(password);
        Token token = tokenRepository.findByTokenAndType(changePasswordToken, TokenType.CHANGE_PASSWORD).orElseThrow(UserExceptionSupplier.invalidToken());
        User user = token.getUser();
        userMapper.toUser(user, userChangePasswordRequest);
        return messageSource.getMessage("PasswordChangesSuccessfully", null, LocaleContextHolder.getLocale());
    }

    @Transactional
    public UserResponse block(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        boolean userIsBlocked = userRepository.isBlocked(id);
        if (userIsBlocked)
        {
            throw UserExceptionSupplier.userAlreadyBlocked().get();
        }
        Role userRole = userRepository.getRole(id);
        if (isSuperAdmin(userRole))
        {
            throw UserExceptionSupplier.superAdminBlocked().get();
        }
        user.setIsBlocked(Boolean.TRUE);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse unblock(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        boolean userIsBlocked = userRepository.isBlocked(id);
        if (!userIsBlocked)
        {
            throw UserExceptionSupplier.userNotBlocked().get();
        }
        user.setIsBlocked(Boolean.FALSE);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse makeAdmin(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        Role userRole = userRepository.getRole(id);
        if (isAdmin(userRole))
        {
            throw UserExceptionSupplier.userAlreadyAdmin().get();
        }
        if (isSuperAdmin(userRole))
        {
            throw UserExceptionSupplier.superAdminDegradedToAdmin().get();
        }
        user.setRole(Role.ADMIN);
        return userMapper.toUserResponse(user);
    }

    private boolean isAdmin(Role role)
    {
        return role.equals(Role.ADMIN);
    }

    @Transactional
    public UserResponse makeSuperAdmin(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        Role userRole = userRepository.getRole(id);
        if (isSuperAdmin(userRole))
        {
            throw UserExceptionSupplier.userAlreadySuperAdmin().get();
        }
        user.setRole(Role.SUPER_ADMIN);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse makeDriver(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        Role userRole = userRepository.getRole(id);
        if (isDriver(userRole))
        {
            throw UserExceptionSupplier.userAlreadyDriver().get();
        }
        if (isSuperAdmin(userRole))
        {
            throw UserExceptionSupplier.superAdminDegradedToDriver().get();
        }
        user.setRole(Role.DRIVER);
        return userMapper.toUserResponse(user);
    }

    private boolean isDriver(Role role)
    {
        return role.equals(Role.DRIVER);
    }

    public List<UserResponse> findAll(String filterText)
    {
        List<User> users = userRepository.findAll();
        return getFilteredUsers(users, filterText);
    }

    public List<UserResponse> findAllUnblocked(String filterText)
    {
        List<User> unblockedUsers = userRepository.findAllByIsBlocked(Boolean.FALSE);
        return getFilteredUsers(unblockedUsers, filterText);
    }

    public List<UserResponse> findAllBlocked(String filterText)
    {
        List<User> blockedUsers = userRepository.findAllByIsBlocked(Boolean.TRUE);
        return getFilteredUsers(blockedUsers, filterText);
    }

    public List<UserResponse> findAllAdmins(String filterText)
    {
        List<User> adminUsers = userRepository.findAllByRole(Role.ADMIN);
        return getFilteredUsers(adminUsers, filterText);
    }

    public List<UserResponse> findAllSuperAdmins(String filterText)
    {
        List<User> superAdminUsers = userRepository.findAllByRole(Role.SUPER_ADMIN);
        return getFilteredUsers(superAdminUsers, filterText);
    }

    public List<UserResponse> findAllDrivers(String filterText)
    {
        List<User> driverUsers = userRepository.findAllByRole(Role.DRIVER);
        return getFilteredUsers(driverUsers, filterText);
    }

    private List<UserResponse> getFilteredUsers(List<User> users, String filterText)
    {
        if (filterText.isEmpty())
        {
            return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
        }
        return users.stream().filter(user -> user.getFirstName().toLowerCase().contains(filterText.toLowerCase()) || user.getLastName().toLowerCase().contains(filterText.toLowerCase())).map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        Role userRole = user.getRole();
        if (isSuperAdmin(userRole))
        {
            throw UserExceptionSupplier.superAdminDelete().get();
        }
        userRepository.deleteById(user.getId());
    }

    private boolean isSuperAdmin(Role role)
    {
        return role.equals(Role.SUPER_ADMIN);
    }

    public void logout()
    {
        SecurityContextHolder.clearContext();
    }

    @Override
    public User loadUserByUsername(String email)
    {
        return userRepository.findByEmail(email).orElseThrow(UserExceptionSupplier.incorrectEmail());
    }
}
