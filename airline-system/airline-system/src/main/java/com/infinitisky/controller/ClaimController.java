package com.infinitisky.controller;

import com.infinitisky.entity.Claim;
import com.infinitisky.service.ClaimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @GetMapping
    public List<Claim> getAll() {
        return claimService.getAll();
    }

    @GetMapping("/{id}")
    public Claim getById(@PathVariable Long id) {
        return claimService.getById(id);
    }

    @PostMapping
    public Claim save(@Valid @RequestBody Claim claim) {
        return claimService.save(claim);
    }

    @PutMapping("/{id}/status")
    public Claim updateStatus(@PathVariable Long id, @RequestParam String status) {
        return claimService.updateStatus(id, status);
    }

    @GetMapping("/by-email")
    public List<Claim> getByEmail(@RequestParam String email) {
        return claimService.getByEmail(email);
    }
}