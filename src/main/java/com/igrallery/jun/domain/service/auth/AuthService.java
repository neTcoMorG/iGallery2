package com.igrallery.jun.domain.service.auth;

import com.igrallery.jun.domain.entity.User;
import com.igrallery.jun.domain.repository.UserRepository;
import com.igrallery.jun.domain.service.auth.kakao.KakaoAuthService;
import com.igrallery.jun.domain.service.auth.kakao.json.OauthToken;
import com.igrallery.jun.domain.service.auth.kakao.json.UserProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final KakaoAuthService kakaoAuthService;

    @Transactional
    public Optional<User> login (String code) {
        OauthToken token = null;
        UserProfile userProfile = null;

        token = kakaoAuthService.getToken(code);
        userProfile = kakaoAuthService.getProfile(token.getAccess_token());

        Optional<User> userOptional = userRepository.findByEmail(userProfile.getKakao_account().getEmail());
        if (userOptional.isPresent()) { return userOptional; }

        User user = userRepository.save(new User(userProfile.getKakao_account().getProfile().getNickname(),
                userProfile.getKakao_account().getEmail(), userProfile.getKakao_account().getProfile().getProfile_image_url()));
        return Optional.of(user);
    }
}
