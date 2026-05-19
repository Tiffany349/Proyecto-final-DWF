package com.infinitisky.service;

import com.infinitisky.entity.Airline;

import java.util.List;

public interface AirlineService {

    List<Airline> getAll();

    Airline getById(Long id);

    Airline save(Airline airline);

    Airline update(Long id, Airline airline);

    void delete(Long id);
}