package com.teamB.hospitalreservation.controller;

import com.teamB.hospitalreservation.service.HospitalDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospital-data") // 경로를 분리하여 명확하게 함
public class HospitalDataController {

    private final HospitalDataService hospitalDataService;

    /**
     * 외부 API로부터 모든 지역의 병원 데이터를 가져와 DB에 저장합니다.
     * 이 작업은 시간이 오래 걸릴 수 있습니다.
     */
    @PostMapping("/init")
    public ResponseEntity<Map<String, Object>> initializeAllHospitals() {
        Map<String, Object> result = new HashMap<>();
        try {
            log.info("병원 데이터 전체 초기화 시작...");
            // 이 서비스 메소드는 모든 지역을 순회하며 병원 데이터를 저장합니다.
            hospitalDataService.fetchAndSaveAllHospitalData();
            
            result.put("success", true);
            result.put("message", "병원 데이터 초기화가 성공적으로 시작되었습니다. 서버 로그를 확인하여 진행 상황을 모니터링하세요.");
            log.info("병원 데이터 전체 초기화 요청이 정상적으로 접수되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("병원 데이터 초기화 중 심각한 에러 발생", e);
            result.put("success", false);
            result.put("error", "병원 데이터 초기화 실패: " + e.getMessage());
            return ResponseEntity.status(500).body(result);
        }
    }
}