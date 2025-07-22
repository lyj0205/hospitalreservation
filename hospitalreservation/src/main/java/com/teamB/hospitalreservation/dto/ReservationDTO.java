package com.teamB.hospitalreservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservationDTO {
    private Long id;
    private String hospitalName;
    private String reservationTime;
    private String patientName;
}