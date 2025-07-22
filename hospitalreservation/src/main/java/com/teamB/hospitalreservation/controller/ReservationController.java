package com.teamB.hospitalreservation.controller;

import com.teamB.hospitalreservation.dto.ReservationDTO;
import com.teamB.hospitalreservation.entity.Reservation;
import com.teamB.hospitalreservation.repository.HospitalRepository;
import com.teamB.hospitalreservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.format.DateTimeParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final HospitalRepository hospitalRepository;

    // 병원의 하루 예약 가능한 시간 조회
    @GetMapping("/{hospitalId}/available-times")
    public ResponseEntity<?> getAvailableTimes(@PathVariable Long hospitalId, @RequestParam String date) {
        // 병원이 존재하는지 확인
        boolean exists = hospitalRepository.existsById(hospitalId);
        if (!exists) {
            return ResponseEntity.status(404)
                    .body("Error: Hospital with ID " + hospitalId + " not found.");
        }

        LocalDate targetDate;
        try {
            targetDate = LocalDate.parse(date); // 날짜 형식 파싱
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("날짜 형식이 잘못되었습니다. yyyy-MM-dd 를 사용하세요.");
        }

        LocalDateTime startOfDay = targetDate.atTime(9, 0);
        LocalDateTime endOfDay = targetDate.atTime(19, 0);

        // 예약 정보 검색
        List<Reservation> reservations;
        try {
            reservations = reservationRepository.findByHospitalIdAndReservationTimeBetween(hospitalId, startOfDay, endOfDay);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("예약을 검색하는 중 오류가 발생하였습니다.: " + e.getMessage());
        }

        // 예약된 시간 추출
        Set<LocalTime> reservedTimeSet = reservations.stream()
                .map(reservation -> reservation.getReservationTime().toLocalTime())
                .collect(Collectors.toSet());

        // 예약되지 않은 시간 필터링
        List<LocalTime> availableTimes = java.util.stream.IntStream.range(9, 19)
                .mapToObj(hour -> LocalTime.of(hour, 0))
                .filter(time -> !reservedTimeSet.contains(time))
                .collect(Collectors.toList());

        return ResponseEntity.ok(availableTimes);
    }


    @GetMapping("/{hospitalId}/reservations")
    public ResponseEntity<?> getReservations(@PathVariable Long hospitalId, @RequestParam String date) {
        if (!hospitalRepository.existsById(hospitalId)) {
            return ResponseEntity.status(404).body("오류 : 병원을 찾을 수 없습니다.");
        }

        LocalDate targetDate;
        try {
            targetDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("잘못된 날짜 형식입니다.");
        }

        LocalDateTime startOfDay = targetDate.atStartOfDay();
        LocalDateTime endOfDay = targetDate.plusDays(1).atStartOfDay();

        List<ReservationDTO> reservations = reservationRepository
            .findByHospitalIdAndReservationTimeBetween(hospitalId, startOfDay, endOfDay)
            .stream()
            .map(reservation -> new ReservationDTO(
                reservation.getId(),
                reservation.getHospital().getName(),
                reservation.getReservationTime().toString(),
                reservation.getPatientName()
            ))
            .collect(Collectors.toList());

        return ResponseEntity.ok(reservations);
    }

    //예약 생성
    @PostMapping
    public ResponseEntity<String> createReservation(@RequestBody Reservation reservation) {

        boolean exists = reservationRepository.existsByHospitalIdAndReservationTime(
                reservation.getHospital().getId(), reservation.getReservationTime());
        if (exists) {
            return ResponseEntity.badRequest().body("이미 예약 되어있는 시간대입니다.");
        }

        // 예약 저장
        reservationRepository.save(reservation);
        return ResponseEntity.ok("예약이 완료되었습니다.");
    }
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            return ResponseEntity.status(404).body("예약을 찾을 수 없습니다..");
        }
        reservationRepository.deleteById(reservationId);
        return ResponseEntity.ok("예약이 취소되었습니다.");
    }

    @PutMapping("/{id}")
public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation updatedReservation) {
    return reservationRepository.findById(id)
        .map(reservation -> {
            reservation.setReservationTime(updatedReservation.getReservationTime());
            reservation.setPatientName(updatedReservation.getPatientName());
            reservation.setHospital(updatedReservation.getHospital());
            Reservation savedReservation = reservationRepository.save(reservation);
            return ResponseEntity.ok(savedReservation);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}