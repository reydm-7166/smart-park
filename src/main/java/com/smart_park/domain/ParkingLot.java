package com.smart_park.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLot {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = true, updatable = false)
        private UUID lotId;

        @PrePersist
        public void generateLotId() {
                if (this.lotId == null) {
                        this.lotId = UUID.randomUUID();
                }
        }

        @NotEmpty(message = "Cannot be empty.")
        String location;

        @Positive(message = "Capacity should be valid.")
        @Min(5)
        Integer capacity;

        @Positive(message = "CPM should be a valid amount.")
        @Min(30)
        Integer costPerMin;
}
