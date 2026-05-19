package com.infinitisky.controller;

import com.infinitisky.dto.request.AirlineRequest;
import com.infinitisky.entity.Airline;
import com.infinitisky.service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    @GetMapping
    public List<Airline> getAll() {
        return airlineService.getAll();
    }

    @GetMapping("/{id}")
    public Airline getById(@PathVariable Long id) {
        return airlineService.getById(id);
    }

    @PostMapping
    public Airline save(@Valid @RequestBody AirlineRequest request) {
        Airline airline = new Airline();
        airline.setName(request.getName());
        airline.setCountry(request.getCountry());
        return airlineService.save(airline);
    }

    @PutMapping("/{id}")
    public Airline update(@PathVariable Long id, @Valid @RequestBody AirlineRequest request) {
        Airline airline = new Airline();
        airline.setName(request.getName());
        airline.setCountry(request.getCountry());
        return airlineService.update(id, airline);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        airlineService.delete(id);
    }
}