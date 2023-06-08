package com.ciosmak.automotivepartner.user.api;

import com.ciosmak.automotivepartner.user.api.request.UserRequest;
import com.ciosmak.automotivepartner.user.api.response.UserResponse;
import com.ciosmak.automotivepartner.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "users")
@AllArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserApi
{
    private final UserService userService;

    @PostMapping("/create")
    @Operation(summary = "Create user")
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest)
    {
        UserResponse userResponse = userService.create(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Find user")
    public ResponseEntity<UserResponse> find(@PathVariable Long id)
    {
        UserResponse userResponse = userService.find(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/find")
    @Operation(summary = "Find all users")
    public ResponseEntity<List<UserResponse>> findAll()
    {
        List<UserResponse> userResponses = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @GetMapping("/find/unblocked")
    @Operation(summary = "Find all unblocked users")
    public ResponseEntity<List<UserResponse>> findAllUnblocked()
    {
        List<UserResponse> userResponses = userService.findAllUnblocked();
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @GetMapping("/find/blocked")
    @Operation(summary = "Find all blocked users")
    public ResponseEntity<List<UserResponse>> findAllBlocked()
    {
        List<UserResponse> userResponses = userService.findAllBlocked();
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @GetMapping("/is_blocked/{id}")
    @Operation(summary = "Check if user is blocked")
    public ResponseEntity<Boolean> isBlocked(@PathVariable Long id)
    {
        userService.find(id);
        Boolean isBlocked = userService.isBlocked(id);
        return ResponseEntity.status(HttpStatus.OK).body(isBlocked);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete user")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
