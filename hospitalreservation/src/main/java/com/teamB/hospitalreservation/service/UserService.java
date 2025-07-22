package com.teamB.hospitalreservation.service;

import com.teamB.hospitalreservation.dto.SignupRequest;
import com.teamB.hospitalreservation.entity.User;
import com.teamB.hospitalreservation.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// 소셜 회원가입 지원을 위한 import
import lombok.extern.slf4j.Slf4j; 

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    private String maskRrn(String rrn) {
        if (rrn == null || rrn.length() < 7) {
            return rrn;
        }
        return rrn.substring(0, 8) + "******";
    }

    // [일반 회원가입]
    public User registerUser(SignupRequest request) {
        String maskedRrn = maskRrn(request.getRrn());
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setName(request.getName());
        user.setRrn(maskedRrn);
        user.setAddress(request.getAddress());
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(request.getEmail());
        user.setPhone_number(request.getPhone_number());
        user.setProvider(null);         // 일반 회원가입
        user.setProviderId(null);

        return userRepository.save(user);
    }

    // [소셜 회원가입/로그인 처리]
    public User registerOrGetSocialUser(String provider, String providerId, String email, String nickname) {
        // provider, providerId로 먼저 찾음
        return userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> {
                    // 없는 경우 새로 생성(기본값 세팅)
                    User user = new User();
                    user.setProvider(provider);
                    user.setProviderId(providerId);
                    user.setEmail(email);
                    user.setUsername(email); // username을 email로 사용(충돌 시 별도 처리 필요)
                    user.setName(nickname);
                    user.setPassword(null);  // 소셜은 password 없음
                    log.info("소셜 신규가입: provider={}, providerId={}, email={}", provider, providerId, email);
                    return userRepository.save(user);
                });
    }

    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}