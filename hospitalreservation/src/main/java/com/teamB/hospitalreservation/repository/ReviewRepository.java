package com.teamB.hospitalreservation.repository;

import com.teamB.hospitalreservation.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByHospitalId(Long hospitalId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.hospital.id = :hospitalId")
    Double findAverageRatingByHospitalId(@Param("hospitalId") Long hospitalId);
}