package com.teamB.hospitalreservation.controller;

import com.teamB.hospitalreservation.dto.EmailSendRequest;
import com.teamB.hospitalreservation.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    // 1. 인증코드 발송 요청
    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendCode(@RequestBody EmailSendRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            emailVerificationService.sendVerificationCode(request.getEmail());
            result.put("success", true);
            result.put("message", "인증코드를 이메일로 전송했습니다.");
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyCode(@RequestParam String email, @RequestParam String code) {
        Map<String, Object> result = new HashMap<>();
        boolean success = emailVerificationService.verifyCode(email, code);
        result.put("success", success);
        result.put("message", success ? "이메일 인증 성공!" : "인증 실패 또는 코드 만료.");
        if (success) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}