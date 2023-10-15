package com.ciosmak.automotivepartner.token.api;

import com.ciosmak.automotivepartner.token.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "tokens")
@AllArgsConstructor
@RestController
@RequestMapping("/api/tokens")
public class TokenApi
{
    private final TokenService tokenService;

    @GetMapping("/verify-email")
    @Operation(summary = "Verify email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token, final HttpServletRequest request)
    {
        String message = tokenService.verifyEmail(token, request);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
