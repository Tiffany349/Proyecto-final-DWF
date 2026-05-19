package com.infinitisky.service.impl;

import com.infinitisky.dto.request.FlightRequest;
import com.infinitisky.entity.Aircraft;
import com.infinitisky.entity.Airline;
import com.infinitisky.entity.Flight;
import com.infinitisky.repository.AircraftRepository;
import com.infinitisky.repository.AirlineRepository;
import com.infinitisky.repository.FlightRepository;
import com.infinitisky.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;

    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));
    }

    @Override
    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight saveFromRequest(FlightRequest request) {
        Flight flight = buildFromRequest(new Flight(), request);
        return flightRepository.save(flight);
    }

    @Override
    public Flight update(Long id, Flight flight) {
        Flight existing = getById(id);
        existing.setOrigin(flight.getOrigin());
        existing.setDestination(flight.getDestination());
        existing.setDepartureTime(flight.getDepartureTime());
        existing.setPrice(flight.getPrice());
        existing.setAvailableSeats(flight.getAvailableSeats());
        existing.setAirline(flight.getAirline());
        existing.setAircraft(flight.getAircraft());
        return flightRepository.save(existing);
    }

    @Override
    public Flight updateFromRequest(Long id, FlightRequest request) {
        Flight existing = getById(id);
        buildFromRequest(existing, request);
        return flightRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        flightRepository.deleteById(id);
    }

    private Flight buildFromRequest(Flight flight, FlightRequest request) {
        flight.setOrigin(request.getOrigin());
        flight.setDestination(request.getDestination());
        flight.setDepartureTime(request.getDepartureTime());
        flight.setPrice(request.getPrice());
        flight.setAvailableSeats(request.getAvailableSeats());

        if (request.getAirlineId() != null) {
            Airline airline = airlineRepository.findById(request.getAirlineId())
                    .orElseThrow(() -> new RuntimeException("Aerolínea no encontrada"));
            flight.setAirline(airline);
        }

        if (request.getAircraftId() != null) {
            Aircraft aircraft = aircraftRepository.findById(request.getAircraftId())
                    .orElseThrow(() -> new RuntimeException("Avión no encontrado"));
            flight.setAircraft(aircraft);
        }

        return flight;
    }
}