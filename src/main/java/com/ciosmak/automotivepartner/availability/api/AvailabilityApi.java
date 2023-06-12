package com.ciosmak.automotivepartner.availability.api;

import com.ciosmak.automotivepartner.availability.api.request.AvailabilityRequest;
import com.ciosmak.automotivepartner.availability.api.response.AvailabilityResponse;
import com.ciosmak.automotivepartner.availability.service.AvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "availability")
@AllArgsConstructor
@RestController
@RequestMapping("api/availability")
public class AvailabilityApi
{
    private final AvailabilityService availabilityService;

    @GetMapping("/check/{user_id}")
    @Operation(summary = "Check if availability is submitted")
    public ResponseEntity<Boolean> isSubmitted(@PathVariable Long user_id)
    {
        Boolean availabilityResponse = availabilityService.isSubmitted(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(availabilityResponse);
    }

    @PostMapping("submit")
    @Operation(summary = "Submit availability")
    public ResponseEntity<AvailabilityResponse> submit(@RequestBody AvailabilityRequest availabilityRequest)
    {
        AvailabilityResponse availabilityResponse = availabilityService.submit(availabilityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(availabilityResponse);
    }
}
