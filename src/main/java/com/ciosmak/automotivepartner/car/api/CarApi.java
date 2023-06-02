package com.ciosmak.automotivepartner.car.api;

import com.ciosmak.automotivepartner.car.api.request.CarRequest;
import com.ciosmak.automotivepartner.car.api.request.UpdateCarRequest;
import com.ciosmak.automotivepartner.car.api.response.CarResponse;
import com.ciosmak.automotivepartner.car.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("api/cars")
public class CarApi
{
    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarResponse> create(@RequestBody CarRequest carRequest)
    {
        CarResponse carResponse = carService.create(carRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(carResponse);
    }

    //TODO w przyszłości zmienić na @Path Variable
    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> find(@PathVariable Long id)
    {
        CarResponse carResponse = carService.find(id);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @PutMapping
    public ResponseEntity<CarResponse> update(@RequestBody UpdateCarRequest updateCarRequest)
    {
        CarResponse carResponse = carService.update(updateCarRequest);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> findAll()
    {
        List<CarResponse> carResponses = carService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(carResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
