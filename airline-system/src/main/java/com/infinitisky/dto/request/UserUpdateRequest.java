package com.infinitisky.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserUpdateRequest {

    @NotBlank(message = "El nombre es requerido")
    private String fullname;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es requerido")
    private String email;
}