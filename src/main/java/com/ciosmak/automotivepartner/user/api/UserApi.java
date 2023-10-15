package com.ciosmak.automotivepartner.user.api;

import com.ciosmak.automotivepartner.user.api.request.UserLoginDataRequest;
import com.ciosmak.automotivepartner.user.api.request.UserRequest;
import com.ciosmak.automotivepartner.user.api.request.UserRestartPasswordRequest;
import com.ciosmak.automotivepartner.user.api.response.UserResponse;
import com.ciosmak.automotivepartner.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    @Operation(summary = "Register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest, final HttpServletRequest request)
    {
        UserResponse userResponse = userService.register(userRequest, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<UserResponse> login(@RequestBody UserLoginDataRequest userLoginDataRequest)
    {
        UserResponse userResponse = userService.login(userLoginDataRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot password")
    public ResponseEntity<String> forgotPassword(@RequestBody String emailRequest, final HttpServletRequest request)
    {
        String message = userService.forgotPassword(emailRequest, request);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/restart-password")
    @Operation(summary = "Restart password")
    public ResponseEntity<String> restartPassword(@RequestBody UserRestartPasswordRequest userRestartPasswordRequest)
    {
        String message = userService.restartPassword(userRestartPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/block/{id}")
    @Operation(summary = "Block")
    public ResponseEntity<UserResponse> block(@PathVariable Long id)
    {
        UserResponse userResponse = userService.block(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PutMapping("/unblock/{id}")
    @Operation(summary = "Unblock")
    public ResponseEntity<UserResponse> unblock(@PathVariable Long id)
    {
        UserResponse userResponse = userService.unblock(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PutMapping("/make-admin/{id}")
    @Operation(summary = "Make admin")
    public ResponseEntity<UserResponse> makeAdmin(@PathVariable Long id)
    {
        UserResponse userResponse = userService.makeAdmin(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PutMapping("/make-driver/{id}")
    @Operation(summary = "Make driver")
    public ResponseEntity<UserResponse> makeDriver(@PathVariable Long id)
    {
        UserResponse userResponse = userService.makeDriver(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/find")
    @Operation(summary = "Find all")
    public ResponseEntity<List<UserResponse>> findAll(@RequestParam(required = false, defaultValue = "") String filterText)
    {
        List<UserResponse> userResponses = userService.findAll(filterText);
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @GetMapping("/find/unblocked")
    @Operation(summary = "Find all unblocked")
    public ResponseEntity<List<UserResponse>> findAllUnblocked(@RequestParam(required = false, defaultValue = "") String filterText)
    {
        List<UserResponse> userResponses = userService.findAllUnblocked(filterText);
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @GetMapping("/find/blocked")
    @Operation(summary = "Find all blocked")
    public ResponseEntity<List<UserResponse>> findAllBlocked(@RequestParam(required = false, defaultValue = "") String filterText)
    {
        List<UserResponse> userResponses = userService.findAllBlocked(filterText);
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @GetMapping("/find/admins")
    @Operation(summary = "Find all admins")
    public ResponseEntity<List<UserResponse>> findAllAdmins(@RequestParam(required = false, defaultValue = "") String filterText)
    {
        List<UserResponse> userResponses = userService.findAllAdmins(filterText);
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @GetMapping("/find/drivers")
    @Operation(summary = "Find all drivers")
    public ResponseEntity<List<UserResponse>> findAllDrivers(@RequestParam(required = false, defaultValue = "") String filterText)
    {
        List<UserResponse> userResponses = userService.findAllDrivers(filterText);
        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //TODO
    /*@PostMapping("/logout/{id}")
    @Operation(summary = "Logout")
    public ResponseEntity<Void> logout(@PathVariable Long id)
    {
        userService.logout(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/
}
