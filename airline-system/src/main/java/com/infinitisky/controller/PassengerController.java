package com.infinitisky.controller;

import com.infinitisky.dto.request.PassengerRequest;
import com.infinitisky.dto.response.PassengerResponse;
import com.infinitisky.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping
    public List<PassengerResponse> getAll() {
        return passengerService.getAll();
    }

    @GetMapping("/{id}")
    public PassengerResponse getById(@PathVariable Long id) {
        return passengerService.getById(id);
    }

    @PostMapping
    public PassengerResponse save(
            @Valid @RequestBody PassengerRequest request
    ) {
        return passengerService.save(request);
    }

    @PutMapping("/{id}")
    public PassengerResponse update(
            @PathVariable Long id,
            @Valid @RequestBody PassengerRequest request
    ) {
        return passengerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        passengerService.delete(id);
    }
}