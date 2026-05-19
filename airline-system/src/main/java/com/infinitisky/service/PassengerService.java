package com.infinitisky.service;

import com.infinitisky.dto.request.PassengerRequest;
import com.infinitisky.dto.response.PassengerResponse;

import java.util.List;

public interface PassengerService {

    List<PassengerResponse> getAll();

    PassengerResponse getById(Long id);

    PassengerResponse save(PassengerRequest request);

    PassengerResponse update(Long id, PassengerRequest request);

    void delete(Long id);
}