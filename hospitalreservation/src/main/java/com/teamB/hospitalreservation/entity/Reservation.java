package com.teamB.hospitalreservation.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.teamB.hospitalreservation.config.FlexibleLocalDateTimeDeserializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @Column(nullable = false)
    @JsonDeserialize(using = FlexibleLocalDateTimeDeserializer.class)
    private LocalDateTime reservationTime;

    @Column(nullable = false)
    private String patientName;
}