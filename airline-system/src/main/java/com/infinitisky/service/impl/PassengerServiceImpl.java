package com.infinitisky.service.impl;

import com.infinitisky.dto.request.PassengerRequest;
import com.infinitisky.dto.response.PassengerResponse;
import com.infinitisky.entity.Passenger;
import com.infinitisky.repository.PassengerRepository;
import com.infinitisky.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    @Override
    public List<PassengerResponse> getAll() {

        return passengerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PassengerResponse getById(Long id) {

        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Passenger not found"));

        return mapToResponse(passenger);
    }

    @Override
    public PassengerResponse save(PassengerRequest request) {

        passengerRepository.findByPassport(request.getPassport())
                .ifPresent(p -> {
                    throw new RuntimeException("Passport already exists");
                });

        Passenger passenger = Passenger.builder()
                .fullname(request.getFullname())
                .birthDate(request.getBirthDate())
                .passport(request.getPassport())
                .build();

        Passenger saved = passengerRepository.save(passenger);

        return mapToResponse(saved);
    }

    @Override
    public PassengerResponse update(
            Long id,
            PassengerRequest request
    ) {

        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Passenger not found"));

        passenger.setFullname(request.getFullname());
        passenger.setBirthDate(request.getBirthDate());
        passenger.setPassport(request.getPassport());

        Passenger updated = passengerRepository.save(passenger);

        return mapToResponse(updated);
    }

    @Override
    public void delete(Long id) {
        passengerRepository.deleteById(id);
    }

    private PassengerResponse mapToResponse(Passenger passenger) {

        return PassengerResponse.builder()
                .id(passenger.getId())
                .fullname(passenger.getFullname())
                .passport(passenger.getPassport())
                .build();
    }
}