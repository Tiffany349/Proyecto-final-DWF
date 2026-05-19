package com.infinitisky.controller;

import com.infinitisky.dto.request.FlightRequest;
import com.infinitisky.entity.Flight;
import com.infinitisky.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    public List<Flight> getAll() {
        return flightService.getAll();
    }

    @GetMapping("/{id}")
    public Flight getById(@PathVariable Long id) {
        return flightService.getById(id);
    }

    @PostMapping
    public Flight save(@Valid @RequestBody FlightRequest request) {
        return flightService.saveFromRequest(request);
    }

    @PutMapping("/{id}")
    public Flight update(@PathVariable Long id, @Valid @RequestBody FlightRequest request) {
        return flightService.updateFromRequest(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        flightService.delete(id);
    }
}