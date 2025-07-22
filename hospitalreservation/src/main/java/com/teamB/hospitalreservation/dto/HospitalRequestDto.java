package com.teamB.hospitalreservation.dto;

import com.teamB.hospitalreservation.entity.Hospital;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 클라이언트의 요청을 받을 DTO
@Getter
@NoArgsConstructor
public class HospitalRequestDto {

    private String name;
    private String address;
    private String phone;
    private String sidoCode;
    private String sgguCode;
    private String apiId;
    private String subjects;

    // DTO를 Hospital 엔티티로 변환하는 메소드
    public Hospital toEntity() {
        Hospital hospital = new Hospital(
                this.name,
                this.address,
                this.phone,
                this.sidoCode,
                this.sgguCode
        );
        hospital.setApiId(this.apiId);
        hospital.setSubjects(this.subjects);
        return hospital;
    }
}