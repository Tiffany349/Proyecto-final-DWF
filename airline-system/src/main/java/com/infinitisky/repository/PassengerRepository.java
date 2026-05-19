package com.infinitisky.repository;

import com.infinitisky.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findByPassport(String passport);
}