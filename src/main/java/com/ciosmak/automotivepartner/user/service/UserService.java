package com.ciosmak.automotivepartner.user.service;

import com.ciosmak.automotivepartner.user.api.request.UserRequest;
import com.ciosmak.automotivepartner.user.api.response.UserResponse;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import com.ciosmak.automotivepartner.user.support.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse create(UserRequest userRequest)
    {
        User user = userRepository.save(userMapper.toUser(userRequest));
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

    public void delete(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        userRepository.deleteById(user.getId());
    }
}
