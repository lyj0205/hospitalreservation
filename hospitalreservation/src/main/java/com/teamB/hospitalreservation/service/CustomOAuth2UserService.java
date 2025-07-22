package com.teamB.hospitalreservation.service;

import com.teamB.hospitalreservation.entity.User;
import com.teamB.hospitalreservation.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // "google" 또는 "kakao"
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = null;
        String nickname = null;
        String providerId = null;

        if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            email = (String) kakaoAccount.get("email");
            nickname = (String) profile.get("nickname");
            providerId = String.valueOf(attributes.get("id"));
        } else if ("google".equals(registrationId)) {
            email = (String) attributes.get("email");
            nickname = (String) attributes.get("name");
            providerId = (String) attributes.get("sub");
        }

        final String finalEmail = email;
        final String finalNickname = nickname;
        final String finalProviderId = providerId;
        final String finalProvider = registrationId;

        // provider + providerId 조합으로 사용자 조회, 없으면 생성
        User user = userRepository.findByProviderAndProviderId(finalProvider, finalProviderId)
                .orElseGet(() -> {
                    User newUser = new User(finalNickname, finalEmail, finalProvider, finalProviderId);
                    return userRepository.save(newUser);
                });

        // 필요한 경우 user 객체 갱신도 가능

        return oAuth2User;
    }
}