package com.infinitisky.service;

import com.infinitisky.dto.request.FlightRequest;
import com.infinitisky.entity.Flight;
import java.util.List;

public interface FlightService {
    List<Flight> getAll();
    Flight getById(Long id);
    Flight save(Flight flight);
    Flight saveFromRequest(FlightRequest request);
    Flight update(Long id, Flight flight);
    Flight updateFromRequest(Long id, FlightRequest request);
    void delete(Long id);
}