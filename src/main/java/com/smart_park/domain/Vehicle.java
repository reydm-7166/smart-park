package com.smart_park.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private Integer parked_in;

    @NotEmpty(message = "Plate number is required.")
    private String plateNumber;

    @NotNull(message = "Type is required.")
    private Type type;

    @NotEmpty(message = "Name is required.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain letters only")
    private String name;
}
