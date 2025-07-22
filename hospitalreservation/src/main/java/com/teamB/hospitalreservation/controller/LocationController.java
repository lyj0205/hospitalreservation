package com.teamB.hospitalreservation.controller;

import com.teamB.hospitalreservation.dto.LocationDto;
import com.teamB.hospitalreservation.repository.HospitalRepository;
import com.teamB.hospitalreservation.repository.LocationRepository;
import com.teamB.hospitalreservation.service.LocationDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationRepository locationRepository;
    private final LocationDataService locationDataService;
    private final HospitalRepository hospitalRepository; // 외래 키 제약 해결을 위해 주입

    /**
     * 전체 Location 목록을 조회합니다.
     */
    @GetMapping
    public List<LocationDto> getLocations() {
        return locationRepository.findAll().stream()
                .map(location -> new LocationDto(
                        location.getName(),
                        location.getSidoCode(),
                        location.getSgguCode()))
                .collect(Collectors.toList());
    }

    /**
     * [수정] Location 데이터를 초기화합니다.
     * 데이터가 이미 존재할 경우, 중복 실행을 방지하여 기존 데이터를 보호합니다.
     */
    @PostMapping("/init")
    @Transactional
    public Map<String, Object> initializeLocations() {
        Map<String, Object> result = new HashMap<>();
        try {
            // 1. [수정] Location 데이터가 이미 존재하는지 먼저 확인합니다.
            if (locationRepository.count() > 0) {
                log.info("Location 데이터가 이미 존재하므로 초기화를 건너뜁니다.");
                result.put("success", true);
                result.put("message", "Location 데이터가 이미 존재합니다. (기존 데이터 " + locationRepository.count() + "건)");
                return result;
            }

            // 2. [수정] 데이터가 없을 경우에만 초기화를 진행합니다. (병원 데이터 삭제 로직 제거)
            log.info("Location 데이터 초기화를 시작합니다...");
            
            locationDataService.fetchAndSaveLocationData();
            long afterCount = locationRepository.count();

            result.put("success", true);
            result.put("message", String.format("성공적으로 %d개의 Location 데이터를 초기화했습니다.", afterCount));
            result.put("newLocationCount", afterCount);
            result.put("timestamp", LocalDateTime.now());

        } catch (Exception e) {
            log.error("Location 데이터 초기화 중 오류가 발생했습니다.", e);
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("timestamp", LocalDateTime.now());
        }
        return result;
    }
}