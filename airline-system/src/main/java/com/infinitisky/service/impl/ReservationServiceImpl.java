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

        Flight flight = reservation.getFlight();

        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        flight.setAvailableSeats(
                flight.getAvailableSeats() - 1
        );

        flightRepository.save(flight);

        reservation.setStatus("CONFIRMED");

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation cancel(Long id) {

        Reservation reservation = getById(id);

        reservation.setStatus("CANCELLED");

        Flight flight = reservation.getFlight();

        flight.setAvailableSeats(
                flight.getAvailableSeats() + 1
        );

        flightRepository.save(flight);

        return reservationRepository.save(reservation);
    }
}