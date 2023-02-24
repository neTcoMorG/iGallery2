package com.igrallery.jun.web.controller;

import com.igrallery.jun.domain.dto.User;
import com.igrallery.jun.domain.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GalleryRepository galleryRepository;

    @GetMapping
    public String homePage (Model model, HttpServletRequest request) {
        if (isLogin(request.getSession(false))) {
            model.addAttribute("login", true);
        }
        return "index";
    }

    public boolean isLogin (HttpSession session) {
        return session != null && session.getAttribute("email") != null;
    }
}
