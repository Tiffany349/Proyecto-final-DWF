package com.infinitisky.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AirlineRequest {

    @NotBlank(message = "El nombre de la aerolínea es requerido")
    private String name;

    @NotBlank(message = "El país de origen es requerido")
    private String country;
}