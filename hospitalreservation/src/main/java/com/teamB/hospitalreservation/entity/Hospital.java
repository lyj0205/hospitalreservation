package com.teamB.hospitalreservation.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    // [핵심 수정] 이 필드들은 Location 관계를 통해 값이 채워지므로, 직접적인 insert/update를 막습니다.
    @Column(name = "sido_code", nullable = false, insertable = false, updatable = false)
    private String sidoCode;

    // [핵심 수정] 이 필드들은 Location 관계를 통해 값이 채워지므로, 직접적인 insert/update를 막습니다.
    @Column(name = "sggu_code", nullable = false, insertable = false, updatable = false)
    private String sgguCode;

    @Column(unique = true)
    private String apiId;

    @Column(length = 1000)
    private String subjects;

    // 이 관계 필드가 sido_code와 sggu_code 컬럼의 실제 "주인" 역할을 합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "sido_code", referencedColumnName = "sidoCode"),
        @JoinColumn(name = "sggu_code", referencedColumnName = "sgguCode")
    })
    private Location location;

    public Hospital(String name, String address, String phone, String sidoCode, String sgguCode) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.sidoCode = sidoCode;
        this.sgguCode = sgguCode;
    }
}