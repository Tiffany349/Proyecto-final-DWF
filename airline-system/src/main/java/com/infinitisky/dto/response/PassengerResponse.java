package com.infinitisky.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerResponse {

    private Long id;

    private String fullname;

    private String passport;
}