package com.igrallery.jun.domain.service.auth.kakao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = KakaoAuthService.class)
class KakaoAuthServiceTest {

    private KakaoAuthService kakaoAuthService;

    @Test
    @DisplayName("OAuth 엑세스 토큰 예외 테스트")
    void getToken_test_exception () {
        //given
        String code = "test";

        //when then
        assertThrows(Exception.class, () -> kakaoAuthService.getToken(code));
    }
}