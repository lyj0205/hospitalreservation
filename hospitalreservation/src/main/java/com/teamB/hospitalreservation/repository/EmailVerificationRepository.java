package com.teamB.hospitalreservation.repository;

import com.teamB.hospitalreservation.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByEmailAndCodeAndVerifiedFalse(String email, String code);
}