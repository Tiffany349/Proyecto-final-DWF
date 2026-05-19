package com.infinitisky.controller;

import com.infinitisky.entity.Aircraft;
import com.infinitisky.repository.AircraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
@RequiredArgsConstructor
public class AircraftController {

    private final AircraftRepository aircraftRepository;

    @GetMapping
    public List<Aircraft> getAll() {
        return aircraftRepository.findAll();
    }

    @GetMapping("/{id}")
    public Aircraft getById(@PathVariable Long id) {
        return aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avión no encontrado"));
    }

    @PostMapping
    public Aircraft save(@RequestBody Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    @PutMapping("/{id}")
    public Aircraft update(@PathVariable Long id, @RequestBody Aircraft aircraft) {
        Aircraft existing = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avión no encontrado"));
        existing.setModel(aircraft.getModel());
        existing.setCapacity(aircraft.getCapacity());
        return aircraftRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        aircraftRepository.deleteById(id);
    }
}