package com.infinitisky.repository;

import com.infinitisky.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByEmail(String email);
    List<Claim> findByClaimType(String claimType);
}