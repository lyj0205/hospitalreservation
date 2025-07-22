package com.teamB.hospitalreservation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(unique = true, length = 20)
    private String rrn;

    @Column(length = 100)
    private String address;

    @Column(unique = true, length = 50)
    private String username;

    @Column
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column
    private String phone_number;

    // 소셜 로그인 추가 컬럼
    @Column(length = 20)
    private String provider; // 예: "google", "kakao"

    @Column(length = 100)
    private String providerId; // 소셜 서비스의 유니크 사용자 ID

    // 소셜 로그인을 위한 생성자
    public User(String name, String email, String provider, String providerId) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.address = null; // 소셜 로그인시 address 명시적으로 null 처리
    }
}