package com.ciosmak.automotivepartner.user.service;

import com.ciosmak.automotivepartner.email.api.request.EmailRequest;
import com.ciosmak.automotivepartner.email.repository.EmailRepository;
import com.ciosmak.automotivepartner.email.service.EmailService;
import com.ciosmak.automotivepartner.user.api.request.UserRequest;
import com.ciosmak.automotivepartner.user.api.response.UserResponse;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import com.ciosmak.automotivepartner.user.support.UserMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailRepository emailRepository;
    private final EmailService emailService;

    public UserResponse register(UserRequest userRequest)
    {
        if (areUserDataIncorrect(userRequest))
        {
            throw UserExceptionSupplier.incorrectUserData().get();
        }

        String email = userRequest.getEmail();

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent())
        {
            throw UserExceptionSupplier.emailTaken().get();
        }

        emailRepository.findByEmail(email).orElseThrow(UserExceptionSupplier.emailNotInDatabase());

        User user = userRepository.save(userMapper.toUser(userRequest));

        EmailRequest emailRequest = new EmailRequest(email);
        emailService.delete(emailRequest);

        return userMapper.toUserResponse(user);
    }

    private boolean areUserDataIncorrect(UserRequest userRequest)
    {
        return userRequest.getFirstName().isEmpty() ||
                userRequest.getLastName().isEmpty() ||
                userRequest.getPassword().isEmpty() ||
                userRequest.getPhoneNumber().isEmpty() ||
                userRequest.getRole().isEmpty();
    }

    @Transactional
    public UserResponse block(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        boolean userIsBlocked = userRepository.isBlocked(id);
        if (userIsBlocked)
        {
            throw UserExceptionSupplier.userBlocked(id).get();
        }
        user.setBlocked(Boolean.TRUE);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse unblock(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        boolean userIsBlocked = userRepository.isBlocked(id);
        if (!userIsBlocked)
        {
            throw UserExceptionSupplier.userUnblocked(id).get();
        }
        user.setBlocked(Boolean.FALSE);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse makeAdmin(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        String userRole = userRepository.getRole(id);
        if (isAdmin(userRole))
        {
            throw UserExceptionSupplier.userAdmin(id).get();
        }
        user.setRole("admin");
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse demoteAdmin(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        String userRole = userRepository.getRole(id);
        if (!isAdmin(userRole))
        {
            throw UserExceptionSupplier.userNotAdmin(id).get();
        }
        user.setRole("driver");
        return userMapper.toUserResponse(user);
    }

    private boolean isAdmin(String role)
    {
        return role.equals("admin");
    }

    @Transactional
    public UserResponse changeEmail(Long id, String email)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));

        emailRepository.findByEmail(email).orElseThrow(UserExceptionSupplier.emailNotInDatabase());
        user.setEmail(email);

        EmailRequest emailRequest = new EmailRequest(email);
        emailService.delete(emailRequest);

        return userMapper.toUserResponse(user);
    }

    public UserResponse find(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> findAll()
    {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    public List<UserResponse> findAllUnblocked()
    {
        return userRepository.findAllByBlocked(Boolean.FALSE).stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    public List<UserResponse> findAllBlocked()
    {
        return userRepository.findAllByBlocked(Boolean.TRUE).stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    public List<UserResponse> findAllAdmins()
    {
        return userRepository.findAllByRole("admin").stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    public List<UserResponse> findAllDrivers()
    {
        return userRepository.findAllByRole("driver").stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        userRepository.deleteById(user.getId());
    }
}
