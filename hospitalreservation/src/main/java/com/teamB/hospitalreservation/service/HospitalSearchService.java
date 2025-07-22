package com.teamB.hospitalreservation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teamB.hospitalreservation.api.HospitalApiClient;
import com.teamB.hospitalreservation.dto.HospitalRequestDto;
import com.teamB.hospitalreservation.dto.HospitalResponseDto;

@Service
public class HospitalSearchService {

	private final HospitalApiClient hospitalApiClient;

	public HospitalSearchService(HospitalApiClient hospitalApiClient) {
		this.hospitalApiClient = hospitalApiClient;
	}
	public List<HospitalResponseDto> search(HospitalRequestDto hospitalRequestDto){
		String sidoCd = hospitalRequestDto.getSidoCode();
		String sgguCd = hospitalRequestDto.getSgguCode();

		return hospitalApiClient.callApi(sidoCd, sgguCd, hospitalRequestDto.getSubjects());
	}
}

