package com.ciosmak.automotivepartner.user.service;

import com.ciosmak.automotivepartner.email.api.request.EmailRequest;
import com.ciosmak.automotivepartner.email.repository.EmailRepository;
import com.ciosmak.automotivepartner.email.service.EmailService;
import com.ciosmak.automotivepartner.user.api.request.UserLoginDataRequest;
import com.ciosmak.automotivepartner.user.api.request.UserRequest;
import com.ciosmak.automotivepartner.user.api.response.UserResponse;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import com.ciosmak.automotivepartner.user.support.UserMapper;
import com.ciosmak.automotivepartner.user.support.event.RegistrationCompleteEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailRepository emailRepository;
    private final EmailService emailService;
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse register(UserRequest userRequest, final HttpServletRequest request)
    {
        checkIfUserDataAreCorrect(userRequest);

        User user = userRepository.save(userMapper.toUser(userRequest));

        EmailRequest emailRequest = new EmailRequest(userRequest.getEmail());
        emailService.delete(emailRequest);

        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));

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
        if (passwordCandidate.isEmpty())
        {
            throw UserExceptionSupplier.emptyPassword().get();
        }
        if (isPasswordTooShort(passwordCandidate))
        {
            throw UserExceptionSupplier.tooShortPassword().get();
        }
        if (isPasswordWeak(passwordCandidate))
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

        Pattern specialCharPattern = Pattern.compile("[~!@#$%^&*()_+{}\\\\[\\\\]:;,.<>/?-]");
        Matcher specialCharMatcher = specialCharPattern.matcher(password);
        if (specialCharMatcher.find())
        {
            hasSpecialChar = true;
        }

        return !(hasLetter && hasDigit && hasSpecialChar);
    }

    public String applicationUrl(HttpServletRequest request)
    {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public UserResponse login(UserLoginDataRequest userLoginDataRequest)
    {
        User user = userRepository.findByEmail(userLoginDataRequest.getEmail()).orElseThrow(UserExceptionSupplier.incorrectLoginData());

        boolean isPasswordCorrect = isPasswordCorrect(userLoginDataRequest.getPassword(), user);
        if (!isPasswordCorrect)
        {
            throw UserExceptionSupplier.incorrectLoginData().get();
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

    public String restartPassword(String emailRequest)
    {
        if (emailRequest.isEmpty())
        {
            throw UserExceptionSupplier.emptyEmail().get();
        }

        if (userRepository.findByEmail(emailRequest).isEmpty())
        {
            throw UserExceptionSupplier.incorrectEmail().get();
        }
        //TODO wywyłanie maila z linkiem do resetu hasła

        return "Link do ustawienia nowego hasła został wysłany na podany adres email.";
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
        String userRole = userRepository.getRole(id);
        if (isAdmin(userRole))
        {
            throw UserExceptionSupplier.userAlreadyAdmin().get();
        }
        user.setRole("admin");
        return userMapper.toUserResponse(user);
    }

    private boolean isAdmin(String role)
    {
        return role.equals("admin");
    }

    @Transactional
    public UserResponse makeDriver(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        String userRole = userRepository.getRole(id);
        if (isDriver(userRole))
        {
            throw UserExceptionSupplier.userAlreadyDriver().get();
        }
        user.setRole("driver");
        return userMapper.toUserResponse(user);
    }

    private boolean isDriver(String role)
    {
        return role.equals("driver");
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
        List<User> adminUsers = userRepository.findAllByRole("admin");
        return getFilteredUsers(adminUsers, filterText);
    }

    public List<UserResponse> findAllDrivers(String filterText)
    {
        List<User> driverUsers = userRepository.findAllByRole("driver");
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
        userRepository.deleteById(user.getId());
    }

    public void logout(Long id)
    {
        userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        //TODO logout
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException
    {
        return userRepository.findByEmail(email).orElseThrow(UserExceptionSupplier.incorrectEmail());
    }
}
