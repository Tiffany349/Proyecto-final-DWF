package com.infinitisky.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
    private Long id;
    private String fullname;
    private String email;
    private String role;
    private boolean active;
}