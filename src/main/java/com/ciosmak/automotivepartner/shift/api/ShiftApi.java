package com.ciosmak.automotivepartner.shift.api;

import com.ciosmak.automotivepartner.shift.api.request.CancelShiftRequest;
import com.ciosmak.automotivepartner.shift.api.request.EndShiftRequest;
import com.ciosmak.automotivepartner.shift.api.request.StartShiftRequest;
import com.ciosmak.automotivepartner.shift.api.request.UpdateFuelRequest;
import com.ciosmak.automotivepartner.shift.api.response.ExtendedShiftResponse;
import com.ciosmak.automotivepartner.shift.api.response.ShiftResponse;
import com.ciosmak.automotivepartner.shift.service.ShiftService;
import com.ciosmak.automotivepartner.shift.support.Type;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "shifts")
@AllArgsConstructor
@RestController
@RequestMapping("api/shifts")
public class ShiftApi
{
    private final ShiftService shiftService;

    @DeleteMapping("/cancel")
    @Operation(summary = "Cancel")
    public ResponseEntity<Void> cancel(@RequestBody CancelShiftRequest cancelShiftRequest)
    {
        shiftService.cancel(cancelShiftRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/start")
    @Operation(summary = "Start")
    public ResponseEntity<ShiftResponse> start(@RequestBody StartShiftRequest startShiftRequest)
    {
        ShiftResponse shiftResponse = shiftService.start(startShiftRequest);
        return ResponseEntity.status(HttpStatus.OK).body(shiftResponse);
    }

    @GetMapping("/check/{id}")
    @Operation(summary = "Is done")
    public ResponseEntity<Boolean> isDone(@PathVariable Long id)
    {
        Boolean isDone = shiftService.isDone(id);
        return ResponseEntity.status(HttpStatus.OK).body(isDone);
    }

    @PostMapping("/end")
    @Operation(summary = "End")
    public ResponseEntity<ShiftResponse> end(@RequestBody EndShiftRequest endShiftRequest)
    {
        ShiftResponse shiftResponse = shiftService.end(endShiftRequest);
        return ResponseEntity.status(HttpStatus.OK).body(shiftResponse);
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate for next week")
    public ResponseEntity<Void> generate()
    {
        shiftService.generate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/find")
    @Operation(summary = "Find all by day and type")
    public ResponseEntity<List<ShiftResponse>> findAllByDayAndType(@RequestParam LocalDate date, @RequestParam Type type)
    {
        List<ShiftResponse> shiftResponses = shiftService.findAllByDayAndType(date, type);
        return ResponseEntity.status(HttpStatus.FOUND).body(shiftResponses);
    }

    @GetMapping("/info/{id}")
    @Operation(summary = "Get info")
    public ResponseEntity<ExtendedShiftResponse> getInfo(@PathVariable Long id)
    {
        ExtendedShiftResponse extendedShiftResponse = shiftService.getInfo(id);
        return ResponseEntity.status(HttpStatus.OK).body(extendedShiftResponse);
    }

    @PutMapping("update-fuel")
    @Operation(summary = "Update fuel consumption")
    public ResponseEntity<ShiftResponse> updateFuel(@RequestBody UpdateFuelRequest updateFuelRequest)
    {
        ShiftResponse shiftResponse = shiftService.updateFuel(updateFuelRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(shiftResponse);
    }

    @GetMapping("/find/car-unavailable")
    @Operation(summary = "Find all with unavailable car since date")
    public ResponseEntity<List<ShiftResponse>> findAllWithUnavailableCarSinceDate(@RequestParam LocalDateTime dateTime)
    {
        List<ShiftResponse> shiftResponse = shiftService.findAllWithUnavailableCarSinceDate(dateTime);
        return ResponseEntity.status(HttpStatus.FOUND).body(shiftResponse);
    }
}
