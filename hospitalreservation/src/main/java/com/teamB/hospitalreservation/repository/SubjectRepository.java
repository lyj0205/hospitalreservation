package com.teamB.hospitalreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamB.hospitalreservation.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

	Subject findByCode(String code);
}
