package com.infinitisky.controller;

import com.infinitisky.entity.Reservation;
import com.infinitisky.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAll();
    }

    @PostMapping
    public Reservation save(@RequestBody Reservation reservation) {
        return reservationService.save(reservation);
    }

    @PutMapping("/cancel/{id}")
    public Reservation cancel(@PathVariable Long id) {
        return reservationService.cancel(id);
    }
}