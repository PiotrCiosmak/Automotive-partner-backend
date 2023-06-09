package com.ciosmak.automotivepartner.user.service;

import com.ciosmak.automotivepartner.email.repository.EmailRepository;
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

    public UserResponse register(UserRequest userRequest)
    {
        if (areUserDataIncorrect(userRequest))
        {
            throw UserExceptionSupplier.incorrectData().get();
        }

        String email = userRequest.getEmail();

        emailRepository.findByEmail(email).orElseThrow(UserExceptionSupplier.emailNotInDatabase());

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent())
        {
            throw UserExceptionSupplier.emailTaken().get();
        }

        User user = userRepository.save(userMapper.toUser(userRequest));
        return userMapper.toUserResponse(user);
    }

    private boolean areUserDataIncorrect(UserRequest userRequest)
    {
        return userRequest.getFirstName().isEmpty() ||
                userRequest.getLastName().isEmpty() ||
                !userRequest.getEmail().contains("@.") ||
                userRequest.getPassword().isEmpty() ||
                userRequest.getPhoneNumber().isEmpty() ||
                userRequest.getRole().isEmpty();
    }

    @Transactional
    public void block(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        boolean userIsBlocked = userRepository.isBlocked(id);
        if (userIsBlocked)
        {
            throw UserExceptionSupplier.userBlocked(id).get();
        }
        userRepository.setBlockedTrue(user.getId());
    }

    @Transactional
    public void unblock(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        boolean userIsBlocked = userRepository.isBlocked(id);
        if (!userIsBlocked)
        {
            throw UserExceptionSupplier.userUnblocked(id).get();
        }
        userRepository.setBlockedFalse(user.getId());
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
        return userRepository.findAllByBlockedFalse().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    public List<UserResponse> findAllBlocked()
    {
        return userRepository.findAllByBlockedTrue().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        userRepository.deleteById(user.getId());
    }
}
