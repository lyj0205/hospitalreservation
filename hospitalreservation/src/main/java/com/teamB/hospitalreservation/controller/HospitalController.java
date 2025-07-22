package com.teamB.hospitalreservation.controller;

import com.teamB.hospitalreservation.dto.HospitalRequestDto;
import com.teamB.hospitalreservation.entity.Hospital;
import com.teamB.hospitalreservation.repository.HospitalRepository;
import com.teamB.hospitalreservation.service.HospitalDataService;
import com.teamB.hospitalreservation.service.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
@Slf4j
public class HospitalController {

    private final HospitalDataService hospitalDataService;
    private final HospitalService hospitalService;
    private final HospitalRepository hospitalRepository;

    @PostMapping("/init")
    public Map<String, Object> initializeHospitalData() {
        return hospitalDataService.fetchAndSaveAllHospitalData();
    }

    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    @GetMapping("/by-location")
    public List<Hospital> getHospitalsByLocation(@RequestParam String sidoCode, @RequestParam String sgguCode) {
        // [수정] Repository에 정의된 올바른 메소드 이름으로 변경합니다.
        return hospitalRepository.findByLocation(sidoCode, sgguCode);
    }

    @GetMapping("/stats")
    public Map<String, Object> getHospitalStats() {
        return hospitalDataService.getHospitalStatistics();
    }

    @PostMapping
    public ResponseEntity<String> createHospital(@RequestBody HospitalRequestDto requestDto) {
        try {
            hospitalService.saveHospital(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body("병원 정보가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            log.error("개별 병원 등록 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("병원 정보 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}