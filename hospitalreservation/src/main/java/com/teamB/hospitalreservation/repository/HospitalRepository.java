package com.teamB.hospitalreservation.repository;

import com.teamB.hospitalreservation.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query("SELECT h FROM Hospital h JOIN h.location l WHERE l.sidoCode = :sidoCode AND l.sgguCode = :sgguCode")
    List<Hospital> findByLocation(@Param("sidoCode") String sidoCode, @Param("sgguCode") String sgguCode);

    @Query("SELECT h.apiId FROM Hospital h WHERE h.apiId IS NOT NULL")
    Set<String> findAllApiIds();

    // [추가] DB에 저장된 모든 진료과목 문자열을 조회합니다.
    @Query("SELECT h.subjects FROM Hospital h WHERE h.subjects IS NOT NULL AND h.subjects != ''")
    List<String> findAllSubjects();
}