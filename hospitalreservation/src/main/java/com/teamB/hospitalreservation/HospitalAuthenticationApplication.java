package com.teamB.hospitalreservation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.teamB.hospitalreservation.service.LocationDataService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class HospitalAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalAuthenticationApplication.class, args);
	}

	/**
	 * 애플리케이션 시작 시 하드코딩된 지역 데이터를 DB에 초기화합니다.
	 * @param locationDataService 지역 데이터 처리 서비스
	 * @return CommandLineRunner 객체
	 */
	@Bean
	public CommandLineRunner initializeLocationData(LocationDataService locationDataService) {
		return args -> {
			try {
				log.info("Starting location data initialization...");
				locationDataService.fetchAndSaveLocationData();
				log.info("Location data initialization completed successfully");
			} catch (Exception e) {
				log.error("Failed to initialize location data: {}", e.getMessage(), e);
				// 애플리케이션은 계속 실행되도록 예외를 잡아서 처리
			}
		};
	}
}