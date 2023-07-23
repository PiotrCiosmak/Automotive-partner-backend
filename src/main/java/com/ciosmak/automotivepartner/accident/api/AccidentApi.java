package com.ciosmak.automotivepartner.accident.api;

import com.ciosmak.automotivepartner.accident.api.request.AccidentRequest;
import com.ciosmak.automotivepartner.accident.api.resposne.AccidentResponse;
import com.ciosmak.automotivepartner.accident.api.resposne.AdvancedAccidentResponse;
import com.ciosmak.automotivepartner.accident.api.resposne.BaseAccidentResponse;
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
    @Operation(summary = "Report an accident")
    public ResponseEntity<AccidentResponse> report(@RequestBody AccidentRequest accidentRequest)
    {
        AccidentResponse accidentResponse = accidentService.report(accidentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(accidentResponse);
    }

    @GetMapping("/find")
    @Operation(summary = "Find all accidents")
    public ResponseEntity<List<BaseAccidentResponse>> findAllAccidents()
    {
        List<BaseAccidentResponse> baseAccidentResponses = accidentService.findAllAccidents();
        return ResponseEntity.status(HttpStatus.OK).body(baseAccidentResponses);
    }

    @GetMapping("/info/{id}")
    @Operation(summary = "Find accident")
    public ResponseEntity<AdvancedAccidentResponse> findAccident(@PathVariable Long id)
    {
        AdvancedAccidentResponse advancedAccidentResponse = accidentService.findAccident(id);
        return ResponseEntity.status(HttpStatus.OK).body(advancedAccidentResponse);
    }

}
