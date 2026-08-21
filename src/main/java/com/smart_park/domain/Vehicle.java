package com.smart_park.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Plate number is required.")
    private String plateNumber;

    @NotNull(message = "Type is required.")
    @Enumerated(EnumType.STRING)
    private Type type;

    @NotEmpty(message = "Name is required.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain letters only")
    private String name;
}
