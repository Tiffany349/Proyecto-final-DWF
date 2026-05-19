package com.infinitisky.service.impl;

import com.infinitisky.entity.Flight;
import com.infinitisky.entity.Reservation;
import com.infinitisky.repository.FlightRepository;
import com.infinitisky.repository.ReservationRepository;
import com.infinitisky.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    @Override
    public Reservation save(Reservation reservation) {

        if (reservation.getFlight() == null || reservation.getFlight().getId() == null) {
            throw new RuntimeException("Flight is required");
        }

        // Buscar vuelo REAL desde BD
        Flight flight = flightRepository.findById(reservation.getFlight().getId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getAvailableSeats() == null || flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);

        flightRepository.save(flight);

        reservation.setFlight(flight);
        reservation.setStatus("CONFIRMED");

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation cancel(Long id) {

        Reservation reservation = getById(id);

        reservation.setStatus("CANCELLED");

        Flight flight = flightRepository.findById(reservation.getFlight().getId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getAvailableSeats() == null) {
            flight.setAvailableSeats(0);
        }

        flight.setAvailableSeats(flight.getAvailableSeats() + 1);

        flightRepository.save(flight);

        return reservationRepository.save(reservation);
    }
}