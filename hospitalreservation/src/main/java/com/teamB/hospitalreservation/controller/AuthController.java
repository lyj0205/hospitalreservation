package com.teamB.hospitalreservation.controller;

import com.teamB.hospitalreservation.config.JwtUtil;
import com.teamB.hospitalreservation.dto.LoginRequest;
import com.teamB.hospitalreservation.dto.LoginResponse;
import com.teamB.hospitalreservation.dto.SignupRequest;
import com.teamB.hospitalreservation.entity.User;
import com.teamB.hospitalreservation.entity.LoginHistory;
import com.teamB.hospitalreservation.repository.UserRepository;
import com.teamB.hospitalreservation.repository.LoginHistoryRepository;
import com.teamB.hospitalreservation.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            userService.registerUser(request);
            return ResponseEntity.ok(Map.of("success", true, "message", "회원가입 성공!")); // 일관된 포맷
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    // 일반 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);

        boolean success = false;
        String message = "";
        if (user == null) {
            message = "사용자를 찾을 수 없습니다.";
        } else if (user.getPassword() == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            message = "비밀번호가 일치하지 않습니다.";
        } else {
            success = true;
            message = "로그인 성공!";
        }

        // 로그인 이력 저장
        LoginHistory history = new LoginHistory();
        history.setUsername(loginRequest.getUsername());
        history.setLoginTime(new Date());
        history.setSuccess(success);
        history.setMessage(message);
        loginHistoryRepository.save(history);

        if (!success) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", message));
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(Map.of("success", true, "message", message, "token", token));
    }

    // 소셜 로그인 인증 성공 콜백 (JWT 발급 등)
    @GetMapping("/oauth2/success")
    public ResponseEntity<?> socialLoginSuccess(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("success", true, "token", token, "email", email));
    }

    // 회원정보 조회
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "유저 정보 없음"));
        }
        // 민감정보는 직접 가공해 리턴 (예시: password 미포함, 혹은 DTO 사용)
        User user = userOpt.get();
        Map<String, Object> info = new HashMap<>();
        info.put("username", user.getUsername());
        info.put("name", user.getName());
        info.put("email", user.getEmail());
        // 주소, 번호 등 추가
        return ResponseEntity.ok(Map.of("success", true, "user", info));
    }
}