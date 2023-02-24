package com.igrallery.jun.web.controller.oauth;

import com.igrallery.jun.domain.entity.User;
import com.igrallery.jun.domain.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/auth/kakao")
@RequiredArgsConstructor
public class KakaoOAuthCallback {

    private final AuthService authService;

    @GetMapping
    public String callback (String code, HttpServletRequest request) throws IOException {
        User user = getUser(code);

        createSession(request, user);

        return "redirect:/profile/" + user.getEmail();
    }

    private void createSession (HttpServletRequest req, User user) {
        HttpSession session = req.getSession();
        session.setAttribute("email", user.getEmail());
        session.setAttribute("name", user.getThumbnail());
        session.setAttribute("thumbnail", user.getThumbnail());
    }

    private User getUser (String code) {
        return authService.login(code).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "계정 생성 실패"));
    }
}
