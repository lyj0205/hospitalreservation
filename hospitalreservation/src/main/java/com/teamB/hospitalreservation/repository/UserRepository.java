package com.teamB.hospitalreservation.repository;

import com.teamB.hospitalreservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // provider + providerId 조합으로 소셜 사용자 조회 (핵심)
    Optional<User> findByProviderAndProviderId(String provider, String providerId);

}