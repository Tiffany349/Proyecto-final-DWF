package com.infinitisky.service.impl;

import com.infinitisky.entity.Airline;
import com.infinitisky.repository.AirlineRepository;
import com.infinitisky.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    public List<Airline> getAll() {
        return airlineRepository.findAll();
    }

    @Override
    public Airline getById(Long id) {
        return airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found"));
    }

    @Override
    public Airline save(Airline airline) {
        return airlineRepository.save(airline);
    }

    @Override
    public Airline update(Long id, Airline airline) {

        Airline existing = getById(id);

        existing.setName(airline.getName());
        existing.setCountry(airline.getCountry());

        return airlineRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        airlineRepository.deleteById(id);
    }
}