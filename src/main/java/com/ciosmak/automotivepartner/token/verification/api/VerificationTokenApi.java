package com.ciosmak.automotivepartner.token.verification.api;

import com.ciosmak.automotivepartner.token.verification.service.VerificationTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/tokens/verification")
public class VerificationTokenApi
{
    private final VerificationTokenService verificationTokenService;

    @GetMapping("/verify_email")
    @Operation(summary = "Verify email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token)
    {
        String message = verificationTokenService.verifyEmail(token);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
