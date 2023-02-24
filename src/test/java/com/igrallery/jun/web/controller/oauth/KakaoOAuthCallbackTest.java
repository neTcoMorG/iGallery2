package com.igrallery.jun.web.controller.oauth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(KakaoOAuthCallback.class)
class KakaoOAuthCallbackTest {

    @Autowired MockMvc mvc;

    @Test
    void oAuthCallback_test() {

    }
}