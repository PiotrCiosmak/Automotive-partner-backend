package com.ciosmak.automotivepartner.accident.api;

import com.ciosmak.automotivepartner.accident.api.request.AccidentRequest;
import com.ciosmak.automotivepartner.accident.api.request.LateAccidentRequest;
import com.ciosmak.automotivepartner.accident.api.resposne.AccidentResponse;
import com.ciosmak.automotivepartner.accident.api.resposne.BaseAccidentResponse;
import com.ciosmak.automotivepartner.accident.api.resposne.ExtendedAccidentResponse;
import com.ciosmak.automotivepartner.accident.service.AccidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "accidents")
@AllArgsConstructor
@RestController
@RequestMapping("api/accidents")
public class AccidentApi
{
    private final AccidentService accidentService;

    @PostMapping("/report")
    @Operation(summary = "Report")
    public ResponseEntity<AccidentResponse> report(@RequestBody AccidentRequest accidentRequest)
    {
        AccidentResponse accidentResponse = accidentService.report(accidentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(accidentResponse);
    }

    @PutMapping("/change-guilt/{id}")
    @Operation(summary = "Change guilt")
    public ResponseEntity<AccidentResponse> changeGuilt(@PathVariable Long id)
    {
        AccidentResponse accidentResponse = accidentService.changeGuilt(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accidentResponse);
    }

    @PostMapping("/complete-unreported")
    @Operation(summary = "Complete unreported")
    public ResponseEntity<AccidentResponse> completeUnreported(@RequestBody LateAccidentRequest lateAccidentRequest)
    {
        AccidentResponse accidentResponse = accidentService.completeUnreported(lateAccidentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(accidentResponse);
    }

    @GetMapping("/find")
    @Operation(summary = "Find all")
    public ResponseEntity<List<BaseAccidentResponse>> findAll()
    {
        List<BaseAccidentResponse> baseAccidentResponses = accidentService.findAllAccidents();
        return ResponseEntity.status(HttpStatus.FOUND).body(baseAccidentResponses);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Find")
    public ResponseEntity<ExtendedAccidentResponse> find(@PathVariable Long id)
    {
        ExtendedAccidentResponse extendedAccidentResponse = accidentService.findAccident(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(extendedAccidentResponse);
    }
}
