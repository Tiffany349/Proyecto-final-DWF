package com.infinitisky.service;

import com.infinitisky.entity.Claim;
import java.util.List;

public interface ClaimService {
    List<Claim> getAll();
    Claim getById(Long id);
    Claim save(Claim claim);
    Claim updateStatus(Long id, String status);
    List<Claim> getByEmail(String email);
}