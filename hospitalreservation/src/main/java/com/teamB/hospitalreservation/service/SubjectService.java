package com.teamB.hospitalreservation.service;

import com.teamB.hospitalreservation.dto.SubjectDto;
import com.teamB.hospitalreservation.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectService {

    private final HospitalRepository hospitalRepository;

    /**
     * DB에 저장된 모든 병원의 진료과목을 중복 없이, 가나다순으로 정렬하여 조회합니다.
     */
    public List<SubjectDto> getDistinctSubjects() {
        // "내과,소아과", "피부과" 와 같은 문자열 리스트를
        // "내과", "소아과", "피부과" 와 같이 개별 분리하고 중복을 제거한 뒤 정렬합니다.
        return hospitalRepository.findAllSubjects().stream()
                .flatMap(subjectsStr -> Arrays.stream(subjectsStr.split(","))) // 쉼표로 분리하여 스트림을 평탄화
                .map(String::trim)                                              // 각 과목명의 앞뒤 공백 제거
                .filter(s -> !s.isEmpty())                                      // 빈 문자열 제거
                .distinct()                                                     // 중복 제거
                .sorted()                                                       // 가나다순 정렬
                .map(SubjectDto::new)                                           // DTO로 변환
                .collect(Collectors.toList());                                  // 리스트로 수집
    }
}