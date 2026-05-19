package com.infinitisky.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FlightRequest {

    @NotBlank(message = "El origen es requerido")
    private String origin;

    @NotBlank(message = "El destino es requerido")
    private String destination;

    @NotNull(message = "La fecha de salida es requerida")
    @Future(message = "La fecha de salida debe ser futura")
    private LocalDateTime departureTime;

    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double price;

    @NotNull(message = "Los asientos disponibles son requeridos")
    @Min(value = 1, message = "Debe haber al menos 1 asiento disponible")
    private Integer availableSeats;

    private Long airlineId;
    private Long aircraftId;
}