package com.teamB.hospitalreservation.repository;

import com.teamB.hospitalreservation.entity.HospitalReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalReviewRepository extends JpaRepository<HospitalReview, Long> {}