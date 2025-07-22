package com.teamB.hospitalreservation.service;

import com.teamB.hospitalreservation.dto.HospitalRequestDto;
import com.teamB.hospitalreservation.entity.Hospital;
import com.teamB.hospitalreservation.repository.HospitalRepository;
import com.teamB.hospitalreservation.repository.LocationRepository; // [추가]
import jakarta.persistence.EntityNotFoundException; // [추가]
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final LocationRepository locationRepository; // [추가] LocationRepository 주입

    @Transactional
    public void saveHospital(HospitalRequestDto requestDto) {
        // [추가] DB에 저장하기 전, 해당 지역 코드가 Location 테이블에 존재하는지 확인
        boolean locationExists = locationRepository.existsBySidoCodeAndSgguCode(
                requestDto.getSidoCode(),
                requestDto.getSgguCode()
        );

        // [추가] 만약 존재하지 않는다면, 예외를 발생시켜 저장을 중단
        if (!locationExists) {
            throw new EntityNotFoundException(
                    "유효하지 않은 지역 코드입니다: sidoCode=" + requestDto.getSidoCode() +
                    ", sgguCode=" + requestDto.getSgguCode());
        }

        Hospital hospital = requestDto.toEntity();
        hospitalRepository.save(hospital);
    }
}