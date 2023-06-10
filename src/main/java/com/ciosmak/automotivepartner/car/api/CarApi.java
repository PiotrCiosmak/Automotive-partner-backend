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
    @Operation(summary = "Add car")
    public ResponseEntity<CarResponse> add(@RequestBody CarRequest carRequest)
    {
        CarResponse carResponse = carService.add(carRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(carResponse);
    }

    @PutMapping("/block/{id}")
    @Operation(summary = "Block car")
    public ResponseEntity<CarResponse> block(@PathVariable Long id)
    {
        CarResponse carResponse = carService.block(id);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @PutMapping("/unblock/{id}")
    @Operation(summary = "Unblock car")
    public ResponseEntity<CarResponse> unblock(@PathVariable Long id)
    {
        CarResponse carResponse = carService.unblock(id);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Find car")
    public ResponseEntity<CarResponse> find(@PathVariable Long id)
    {
        CarResponse carResponse = carService.find(id);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update car")
    public ResponseEntity<CarResponse> update(@PathVariable Long id, @RequestBody UpdateCarMileageRequest updateCarMileageRequest)
    {
        CarResponse carResponse = carService.update(id, updateCarMileageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @GetMapping("/find")
    @Operation(summary = "Find all cars")
    public ResponseEntity<List<CarResponse>> findAll()
    {
        List<CarResponse> carResponses = carService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(carResponses);
    }

    @GetMapping("/find/unblocked")
    @Operation(summary = "Find all unblocked cars")
    public ResponseEntity<List<CarResponse>> findAllUnblocked()
    {
        List<CarResponse> carResponses = carService.findAllUnblocked();
        return ResponseEntity.status(HttpStatus.OK).body(carResponses);
    }

    @GetMapping("/find/blocked")
    @Operation(summary = "Find all blocked cars")
    public ResponseEntity<List<CarResponse>> findAllBlocked()
    {
        List<CarResponse> carResponses = carService.findAllBlocked();
        return ResponseEntity.status(HttpStatus.OK).body(carResponses);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete car")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
