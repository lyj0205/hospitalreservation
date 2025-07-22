package com.teamB.hospitalreservation.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamB.hospitalreservation.dto.HospitalRequestDto;
import com.teamB.hospitalreservation.dto.HospitalResponseDto;
import com.teamB.hospitalreservation.service.HospitalSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hospital-search")
@RequiredArgsConstructor

public class HospitalSearchController {

    private final HospitalSearchService hospitalSearchService;

    @PostMapping("/search")
    public List<HospitalResponseDto> searchHospitals(@RequestBody HospitalRequestDto requestDto){
        return hospitalSearchService.search(requestDto);
    }
}