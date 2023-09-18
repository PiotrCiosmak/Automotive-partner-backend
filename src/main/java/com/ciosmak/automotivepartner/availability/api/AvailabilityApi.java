package com.ciosmak.automotivepartner.availability.api;

import com.ciosmak.automotivepartner.availability.api.request.WeekAvailabilityRequest;
import com.ciosmak.automotivepartner.availability.api.response.AvailabilityResponse;
import com.ciosmak.automotivepartner.availability.service.AvailabilityService;
import com.ciosmak.automotivepartner.shift.support.Type;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "availability")
@AllArgsConstructor
@RestController
@RequestMapping("api/availability")
public class AvailabilityApi
{
    private final AvailabilityService availabilityService;

    @PostMapping("/submit")
    @Operation(summary = "Submit")
    public ResponseEntity<List<AvailabilityResponse>> submit(@RequestBody WeekAvailabilityRequest weekAvailabilityRequest)
    {
        List<AvailabilityResponse> availabilityRequests = availabilityService.submit(weekAvailabilityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(availabilityRequests);
    }

    @GetMapping("/check/{user_id}")
    @Operation(summary = "Is submitted")
    public ResponseEntity<Boolean> isSubmitted(@PathVariable Long user_id)
    {
        Boolean isSubmitted = availabilityService.isSubmitted(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(isSubmitted);
    }

    @GetMapping("/type/{user_id}")
    @Operation(summary = "Get type")
    public ResponseEntity<Type> getType(@PathVariable Long user_id, @RequestParam LocalDate date)
    {
        Type type = availabilityService.getType(user_id, date);
        return ResponseEntity.status(HttpStatus.OK).body(type);
    }

    @GetMapping("/quantity")
    @Operation(summary = "Get quantity")
    public ResponseEntity<Integer> getQuantity(@RequestParam LocalDate date, @RequestParam Type type)
    {
        Integer numberOfApplicants = availabilityService.getQuantity(date, type);
        return ResponseEntity.status(HttpStatus.OK).body(numberOfApplicants);
    }
}
