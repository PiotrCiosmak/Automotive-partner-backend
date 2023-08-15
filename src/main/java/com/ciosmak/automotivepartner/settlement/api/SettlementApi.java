package com.ciosmak.automotivepartner.settlement.api;

import com.ciosmak.automotivepartner.settlement.api.request.SettlementRequest;
import com.ciosmak.automotivepartner.settlement.api.response.SettlementResponse;
import com.ciosmak.automotivepartner.settlement.service.SettlementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "settlements")
@AllArgsConstructor
@RestController
@RequestMapping("api/settlements")
public class SettlementApi
{
    private final SettlementService settlementService;

    @GetMapping("/info/{user_id}")
    @Operation(summary = "Get info")
    public ResponseEntity<SettlementResponse> getInfo(@PathVariable Long user_id, @RequestParam LocalDate date)
    {
        SettlementResponse settlementResponse = settlementService.getInfo(user_id, date);
        return ResponseEntity.status(HttpStatus.OK).body(settlementResponse);
    }

    @PutMapping("/report_bug/{id}")
    @Operation(summary = "Report bug")
    public ResponseEntity<SettlementResponse> reportBug(@PathVariable Long id)
    {
        SettlementResponse settlementResponse = settlementService.reportBug(id);
        return ResponseEntity.status(HttpStatus.OK).body(settlementResponse);
    }

    @GetMapping("/check/{id}")
    @Operation(summary = "Is bug reported")
    public ResponseEntity<Boolean> isBugReported(@PathVariable Long id)
    {
        Boolean bugIsReported = settlementService.isBugReported(id);
        return ResponseEntity.status(HttpStatus.OK).body(bugIsReported);
    }

    @PostMapping("/complete")
    @Operation(summary = "Complete")
    public ResponseEntity<SettlementResponse> complete(@RequestBody SettlementRequest settlementRequest)
    {
        SettlementResponse settlementResponse = settlementService.complete(settlementRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(settlementResponse);
    }

    @PutMapping("/update")
    @Operation(summary = "Update")
    public ResponseEntity<SettlementResponse> update(@RequestBody SettlementRequest settlementRequest)
    {
        SettlementResponse settlementResponse = settlementService.update(settlementRequest);
        return ResponseEntity.status(HttpStatus.OK).body(settlementResponse);
    }

    @GetMapping("/find")
    @Operation(summary = "Find all with reported bug")
    public ResponseEntity<List<SettlementResponse>> findAllWithReportedBug()
    {
        List<SettlementResponse> settlementResponses = settlementService.findAllWithReportedBug();
        return ResponseEntity.status(HttpStatus.OK).body(settlementResponses);
    }
}
