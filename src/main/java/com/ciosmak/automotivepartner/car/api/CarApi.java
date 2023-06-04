package com.ciosmak.automotivepartner.car.api;

import com.ciosmak.automotivepartner.car.api.request.CarRequest;
import com.ciosmak.automotivepartner.car.api.request.UpdateCarRequest;
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

    @PostMapping
    @Operation(summary = "Create car")
    public ResponseEntity<CarResponse> create(@RequestBody CarRequest carRequest)
    {
        CarResponse carResponse = carService.create(carRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(carResponse);
    }

    //TODO w przyszłości zmienić na @Path Variable
    @GetMapping("/{id}")
    @Operation(summary = "Find car")
    public ResponseEntity<CarResponse> find(@PathVariable Long id)
    {
        CarResponse carResponse = carService.find(id);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @PutMapping
    @Operation(summary = "Update car")
    public ResponseEntity<CarResponse> update(@RequestBody UpdateCarRequest updateCarRequest)
    {
        CarResponse carResponse = carService.update(updateCarRequest);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @GetMapping
    @Operation(summary = "Find all cars")
    public ResponseEntity<List<CarResponse>> findAll()
    {
        List<CarResponse> carResponses = carService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(carResponses);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete car")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
