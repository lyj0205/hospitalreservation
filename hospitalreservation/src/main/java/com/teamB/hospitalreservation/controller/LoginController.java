package com.teamB.hospitalreservation.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("api/loginSuccess")
    public String loginSuccess(OAuth2AuthenticationToken authentication) {
        String name = authentication.getPrincipal().getAttribute("name");
        return "구글 로그인 성공" + name + "님.";
    }

    @GetMapping("api/loginFailure")
    public String loginFailure() {
        return "로그인 실패";
    }
}

