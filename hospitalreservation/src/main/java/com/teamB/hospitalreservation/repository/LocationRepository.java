package com.teamB.hospitalreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamB.hospitalreservation.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

	Location findByName(String name);

	// [추가] sidoCode와 sgguCode로 Location 존재 여부 확인
	boolean existsBySidoCodeAndSgguCode(String sidoCode, String sgguCode);
}