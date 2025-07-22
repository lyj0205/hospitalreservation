//package com.teamB.hospitalreservation;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//import com.teamB.hospitalreservation.entity.Location;
//import com.teamB.hospitalreservation.entity.Subject;
//import com.teamB.hospitalreservation.repository.LocationRepository;
//import com.teamB.hospitalreservation.repository.SubjectRepository;
//
//@SpringBootApplication
//public class 	HospitalSearchApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(HospitalSearchApplication.class, args);
//	}
//
//	@Bean
//	public CommandLineRunner loadData(LocationRepository locationRepository) {
//		return args -> {
//			if(locationRepository.count() == 0) {
//				locationRepository.save(new Location("강남구", "110000", "110019"));
//				locationRepository.save(new Location("마포구", "110000", "110007"));
//				locationRepository.save(new Location("종로구", "110000", "110005"));
//			}
//		};
//
//	}
//
//	@Bean
//	public CommandLineRunner initSubjectData(SubjectRepository subjectRepository) {
//		return args -> {
//			if (subjectRepository.count() == 0) {
//	            subjectRepository.save(new Subject(null, "01","내과"));
//	            subjectRepository.save(new Subject(null, "02","안과"));
//	            subjectRepository.save(new Subject(null, "03", "피부과"));
//	            subjectRepository.save(new Subject(null, "04", "이비인후과"));
//			}
//		};
//	}
//
//}
