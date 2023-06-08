package com.ciosmak.automotivepartner.user.service;

import com.ciosmak.automotivepartner.user.api.request.UserRequest;
import com.ciosmak.automotivepartner.user.api.response.UserResponse;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import com.ciosmak.automotivepartner.user.support.UserMapper;
import com.ciosmak.automotivepartner.user.support.exception.EmailAlreadyExists;
import com.ciosmak.automotivepartner.user.support.exception.IncorrectUserData;
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

    public UserResponse create(UserRequest userRequest)
    {
        String email = userRequest.getEmail();

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent())
        {
            throw new EmailAlreadyExists(email);
        }

        if (areUserDataIncorrect(userRequest))
        {
            throw new IncorrectUserData();
        }

        User user = userRepository.save(userMapper.toUser(userRequest));
        return userMapper.toUserResponse(user);
    }

    private boolean areUserDataIncorrect(UserRequest userRequest)
    {
        return userRequest.getFirstName().isEmpty() ||
                userRequest.getLastName().isEmpty() ||
                userRequest.getEmail().isEmpty() ||
                userRequest.getPassword().isEmpty() ||
                userRequest.getPhoneNumber().isEmpty() ||
                userRequest.getRole().isEmpty();
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

    public Boolean isBlocked(Long id)
    {
        return userRepository.isUserBlocked(id);
    }

    public void delete(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        userRepository.deleteById(user.getId());
    }
}
