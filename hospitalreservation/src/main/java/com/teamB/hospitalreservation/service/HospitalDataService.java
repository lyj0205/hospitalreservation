package com.teamB.hospitalreservation.service;

import com.teamB.hospitalreservation.api.HospitalApiClient;
import com.teamB.hospitalreservation.dto.HospitalResponseDto;
import com.teamB.hospitalreservation.entity.Hospital;
import com.teamB.hospitalreservation.entity.Location;
import com.teamB.hospitalreservation.repository.HospitalRepository;
import com.teamB.hospitalreservation.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class HospitalDataService {

    private final LocationRepository locationRepository;
    private final HospitalRepository hospitalRepository;
    private final HospitalApiClient hospitalApiClient;

    @Transactional
    public Map<String, Object> fetchAndSaveAllHospitalData() {
        Map<String, Object> result = new HashMap<>();

        List<Location> locations = locationRepository.findAll();
        if (locations.isEmpty()) {
            log.warn("병원 데이터를 가져오기 전에 먼저 지역(Location) 데이터를 초기화해야 합니다.");
            result.put("success", false);
            result.put("message", "지역(Location) 데이터가 비어있습니다. 먼저 지역 데이터를 초기화하세요.");
            result.put("timestamp", LocalDateTime.now());
            return result;
        }

        log.info("총 {}개 지역의 병원 데이터 동기화를 시작합니다.", locations.size());

        Set<String> existingApiIds = hospitalRepository.findAllApiIds();
        log.info("DB에 이미 존재하는 병원 API ID {}개를 조회했습니다.", existingApiIds.size());

        // [수정] 1. API 호출은 병렬로 처리하여 결과를 리스트로 수집합니다.
        List<Hospital> allHospitalsToSave = locations.parallelStream()
                .flatMap(location -> {
                    try {
                        List<HospitalResponseDto> hospitalDtos = hospitalApiClient.callApi(location.getSidoCode(), location.getSgguCode(), null);

                        if (hospitalDtos == null || hospitalDtos.isEmpty()) {
                            log.info("지역 [{} {}]에서 조회된 병원 데이터가 없습니다.", location.getSidoCode(), location.getName());
                            return Stream.empty(); // 비어있는 Stream 반환
                        }

                        // DB에 없는 신규 병원 정보만 Hospital 엔티티로 변환
                        return hospitalDtos.stream()
                                .filter(dto -> dto.getApiId() != null && !existingApiIds.contains(dto.getApiId()))
                                .map(dto -> Hospital.builder()
                                        .name(dto.getName())
                                        .address(dto.getAddress())
                                        .phone(dto.getPhone())
                                        .subjects(dto.getSubjects())
                                        .apiId(dto.getApiId())
                                        .location(location)
                                        .build());
                    } catch (Exception e) {
                        log.error("지역 [{} {}] 데이터 처리 중 오류 발생", location.getSidoCode(), location.getName(), e);
                        return Stream.empty(); // 오류 발생 시에도 비어있는 Stream 반환
                    }
                })
                .collect(Collectors.toList());

        // [수정] 2. 수집된 모든 병원 데이터를 메인 스레드에서 한번에 저장합니다. (트랜잭션 내에서 실행)
        if (!allHospitalsToSave.isEmpty()) {
            log.info("총 {}개의 신규 병원을 저장합니다.", allHospitalsToSave.size());
            hospitalRepository.saveAll(allHospitalsToSave);
        }

        long totalSavedCount = allHospitalsToSave.size();
        log.info("병원 데이터 동기화가 완료되었습니다. 총 {}개의 신규 병원이 처리되었습니다.", totalSavedCount);

        result.put("success", true);
        result.put("message", String.format("성공적으로 %d개의 신규 병원 데이터를 저장했습니다.", totalSavedCount));
        result.put("newHospitalCount", totalSavedCount);
        result.put("timestamp", LocalDateTime.now());

        return result;
    }

    public Map<String, Object> getHospitalStatistics() {
        return Collections.singletonMap("totalHospitals", hospitalRepository.count());
    }
}