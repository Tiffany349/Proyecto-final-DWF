package com.infinitisky.controller;

import com.infinitisky.entity.Payment;
import com.infinitisky.entity.Reservation;
import com.infinitisky.repository.PaymentRepository;
import com.infinitisky.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    @GetMapping
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    @PostMapping
    public Payment save(@RequestBody Payment payment) {

        if (payment.getReservation() == null || payment.getReservation().getId() == null) {
            throw new RuntimeException("Se requiere una reserva");
        }

        Reservation reservation = reservationRepository.findById(
                payment.getReservation().getId()
        ).orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (paymentRepository.existsByReservationId(reservation.getId())) {
            throw new RuntimeException("Esta reserva ya fue pagada");
        }

        if (payment.getAmount() == null || payment.getAmount() <= 0) {
            throw new RuntimeException("Monto inválido");
        }

        payment.setReservation(reservation);

        return paymentRepository.save(payment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        paymentRepository.deleteById(id);
    }
}