package com.infinitisky.service.impl;

import com.infinitisky.entity.Claim;
import com.infinitisky.repository.ClaimRepository;
import com.infinitisky.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    @Override
    public List<Claim> getAll() {
        return claimRepository.findAll();
    }

    @Override
    public Claim getById(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reclamo no encontrado"));
    }

    @Override
    public Claim save(Claim claim) {
        return claimRepository.save(claim);
    }

    @Override
    public Claim updateStatus(Long id, String status) {
        Claim claim = getById(id);
        claim.setStatus(status);
        return claimRepository.save(claim);
    }

    @Override
    public List<Claim> getByEmail(String email) {
        return claimRepository.findByEmail(email);
    }
}