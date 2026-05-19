package com.infinitisky.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "claims")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    private String passengerName;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es requerido")
    private String email;

    @NotBlank(message = "El tipo de reclamo es requerido")
    private String claimType;

    @NotBlank(message = "La descripción es requerida")
    @Column(columnDefinition = "TEXT")
    private String description;

    private String flightNumber;
    private String destination;
    private String origin;

    @Builder.Default
    private LocalDateTime claimDate = LocalDateTime.now();

    @Builder.Default
    private String status = "PENDIENTE";

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}