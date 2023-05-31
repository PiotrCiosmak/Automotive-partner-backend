package com.ciosmak.automotivepartner.car.api;

import com.ciosmak.automotivepartner.car.api.request.CarRequest;
import com.ciosmak.automotivepartner.car.api.request.UpdateCarRequest;
import com.ciosmak.automotivepartner.car.api.response.CarResponse;
import com.ciosmak.automotivepartner.car.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO dodane info dla swagger w wielu miejsach

@RestController
@RequestMapping("api/cars")
public class CarApi
{
    private final CarService carService;

    //TODO lombok
    CarApi(CarService carService)
    {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<CarResponse> create(@RequestBody CarRequest carRequest)
    {
        CarResponse carResponse = carService.create(carRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(carResponse);
    }

    //TODO może zmienić na @Path Variable
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

    @GetMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
