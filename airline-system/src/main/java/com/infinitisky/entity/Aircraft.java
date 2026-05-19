package com.infinitisky.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "aircrafts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private Integer capacity;
}