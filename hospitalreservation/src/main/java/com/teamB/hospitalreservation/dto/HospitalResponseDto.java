package com.teamB.hospitalreservation.dto;

import com.teamB.hospitalreservation.entity.Hospital;
import com.teamB.hospitalreservation.entity.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HospitalResponseDto {
    private String name;
    private String address;
    private String phone;
    private String apiId; // API에서 제공하는 병원 고유 ID
    private String subjects; // 진료 과목

    public HospitalResponseDto(String name, String address, String phone, String apiId, String subjects) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.apiId = apiId;
        this.subjects = subjects;
    }

    /**
     * [추가] DTO를 Hospital 엔티티로 변환하는 메소드입니다.
     * 병원의 지역 정보를 설정하기 위해 Location 객체를 파라미터로 받습니다.
     */
    public Hospital toEntity(Location location) {
        return Hospital.builder()
                .name(this.name)
                .address(this.address)
                .phone(this.phone)
                .apiId(this.apiId)
                .subjects(this.subjects)
                .sidoCode(location.getSidoCode()) // Location 정보에서 sidoCode 설정
                .sgguCode(location.getSgguCode()) // Location 정보에서 sgguCode 설정
                .location(location) // 연관관계 설정
                .build();
    }
}