package com.igrallery.jun.web.controller;

import com.igrallery.jun.domain.entity.Gallery;
import com.igrallery.jun.domain.entity.User;
import com.igrallery.jun.domain.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public String profilePage (Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);


        return session == null ?
                "index" :
                profilePage(request.getSession(false).getAttribute("email").toString(), model,  request);
    }

    @GetMapping("/{email}")
    public String profilePage (@PathVariable String email, Model model, HttpServletRequest request) {
        User user = getUser(email);

        List<Gallery> galleries = new ArrayList<>(user.getGalleries());
        model.addAttribute("user", user);
        model.addAttribute("galleries", galleries);
        model.addAttribute("isOwner", isOwner(email, request.getSession(false)));

        return "profile";
    }

    private User getUser (String email) {
        return profileService.getProfile(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저는 존재하지 않습니다"));
    }

    public boolean isOwner (String email, HttpSession session) {
        return session != null && session.getAttribute("email") != null
                && session.getAttribute("email").equals(email);
    }
}
