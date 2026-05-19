package com.infinitisky.service;

import com.infinitisky.entity.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> getAll();

    Reservation getById(Long id);

    Reservation save(Reservation reservation);

    Reservation cancel(Long id);
}