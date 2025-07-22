package com.teamB.hospitalreservation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 별점
    @Column(nullable = false)
    private int rating;

    // 코멘트
    @Column(nullable = false)
    private String comment;

    // HospitalReview와 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_review_id")
    private HospitalReview hospital;

    // 기본 생성자
    public Review() {}


}