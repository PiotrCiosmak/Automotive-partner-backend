package com.ciosmak.automotivepartner.email.api;

import com.ciosmak.automotivepartner.email.api.request.EmailRequest;
import com.ciosmak.automotivepartner.email.api.response.EmailResponse;
import com.ciosmak.automotivepartner.email.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "emails")
@AllArgsConstructor
@RestController
@RequestMapping("api/emails")
public class EmailApi
{
    private final EmailService emailService;

    @PostMapping("/add")
    @Operation(summary = "Add")
    public ResponseEntity<EmailResponse> add(@RequestBody EmailRequest emailRequest)
    {
        EmailResponse emailResponse = emailService.add(emailRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(emailResponse);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete")
    public ResponseEntity<Void> delete(@RequestBody EmailRequest emailRequest)
    {
        emailService.delete(emailRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
