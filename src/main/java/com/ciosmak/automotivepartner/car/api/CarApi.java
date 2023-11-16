package com.ciosmak.automotivepartner.car.api;

import com.ciosmak.automotivepartner.car.api.request.CarRequest;
import com.ciosmak.automotivepartner.car.api.request.UpdateCarMileageRequest;
import com.ciosmak.automotivepartner.car.api.response.CarResponse;
import com.ciosmak.automotivepartner.car.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "cars")
@AllArgsConstructor
@RestController
@RequestMapping("api/cars")
public class CarApi
{
    private final CarService carService;

    @PostMapping("/add")
    @Operation(summary = "Add")
    public ResponseEntity<CarResponse> add(@RequestBody CarRequest carRequest)
    {
        CarResponse carResponse = carService.add(carRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(carResponse);
    }

    @PutMapping("/block/{id}")
    @Operation(summary = "Block")
    public ResponseEntity<CarResponse> block(@PathVariable Long id)
    {
        CarResponse carResponse = carService.block(id);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @PutMapping("/unblock/{id}")
    @Operation(summary = "Unblock")
    public ResponseEntity<CarResponse> unblock(@PathVariable Long id)
    {
        CarResponse carResponse = carService.unblock(id);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @PutMapping("/update-mileage/{id}")
    @Operation(summary = "Update mileage")
    public ResponseEntity<CarResponse> updateMileage(@RequestBody UpdateCarMileageRequest updateCarMileageRequest)
    {
        CarResponse carResponse = carService.updateMileage(updateCarMileageRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(carResponse);
    }

    @GetMapping("/find")
    @Operation(summary = "Find all")
    public ResponseEntity<List<CarResponse>> findAll(@RequestParam(required = false, defaultValue = "") String filterText)
    {
        List<CarResponse> carResponses = carService.findAll(filterText);
        return ResponseEntity.status(HttpStatus.FOUND).body(carResponses);
    }

    @GetMapping("/find/unblocked")
    @Operation(summary = "Find all unblocked")
    public ResponseEntity<List<CarResponse>> findAllUnblocked(@RequestParam(required = false, defaultValue = "") String filterText)
    {
        List<CarResponse> carResponses = carService.findAllUnblocked(filterText);
        return ResponseEntity.status(HttpStatus.FOUND).body(carResponses);
    }

    @GetMapping("/find/blocked")
    @Operation(summary = "Find all blocked")
    public ResponseEntity<List<CarResponse>> findAllBlocked(@RequestParam(required = false, defaultValue = "") String filterText)
    {
        List<CarResponse> carResponses = carService.findAllBlocked(filterText);
        return ResponseEntity.status(HttpStatus.FOUND).body(carResponses);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete car")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
