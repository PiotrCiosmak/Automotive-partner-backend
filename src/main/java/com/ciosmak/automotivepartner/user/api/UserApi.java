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

    @PostMapping("/register")
    @Operation(summary = "Register user")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest)
    {
        UserResponse userResponse = userService.register(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping("/block/{id}")
    @Operation(summary = "Block user")
    public ResponseEntity<UserResponse> block(@PathVariable Long id)
    {
        UserResponse userResponse = userService.block(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PutMapping("unblock/{id}")
    @Operation(summary = "Unblock user")
    public ResponseEntity<UserResponse> unblock(@PathVariable Long id)
    {
        UserResponse userResponse = userService.unblock(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PutMapping("admins/make/{id}")
    @Operation(summary = "Make admin")
    public ResponseEntity<UserResponse> makeAdmin(@PathVariable Long id)
    {
        UserResponse userResponse = userService.makeAdmin(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PutMapping("admins/demote/{id}")
    @Operation(summary = "Demote admin")
    public ResponseEntity<UserResponse> demoteAdmin(@PathVariable Long id)
    {
        UserResponse userResponse = userService.demoteAdmin(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userResponse);
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

    @GetMapping("/find/admins")
    @Operation(summary = "Find all admins")
    public ResponseEntity<List<UserResponse>> findAllAdmins()
    {
        List<UserResponse> userResponses = userService.findAllAdmins();
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @GetMapping("/find/drivers")
    @Operation(summary = "Find all drivers")
    public ResponseEntity<List<UserResponse>> findAllDrivers()
    {
        List<UserResponse> userResponses = userService.findAllDrivers();
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete user")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
