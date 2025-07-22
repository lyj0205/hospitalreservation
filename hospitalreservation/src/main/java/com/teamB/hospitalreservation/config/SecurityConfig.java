package com.teamB.hospitalreservation.config;

import com.teamB.hospitalreservation.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF 보호 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                // 2. CORS 설정 적용
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 3. Form 기반 로그인 및 HTTP Basic 인증 비활성화 (JSON/JWT 기반이므로)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 4. 요청 경로별 인가 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/signup",
                                "/api/login",
                                "/api/auth/**",
                                "/api/email/**",
                                "/api/subjects",
                                "/api/subject-db",
                                "/api/hospital-search/**",
                                "/api/hospital-db/**",
                                "/api/hospital-data/**",
                                "/api/locations/**",
                                "/api/hospitals/**",
                                "/loginSuccess", // OAuth2 로그인 성공/실패 URL도 허용
                                "/loginFailure",
                                "/error"
                        ).permitAll()
                        .anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
                )

                // 5. OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("/loginSuccess")
                        .failureUrl("/loginFailure")
                );

        // 6. 세션 관리: STATELESS 설정을 제거하여 oauth2Login이 세션을 사용할 수 있도록 허용
        // 이렇게 하면 JWT를 사용하는 API는 세션 없이, OAuth2 로그인은 세션을 사용하며 동작합니다.

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 이전에 사용하시던 특정 Origin 허용 방식으로 되돌립니다.
        config.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // 특정 Origin을 명시했으므로 true로 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // /api/ 로 시작하는 경로에만 CORS 설정을 적용합니다.
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}