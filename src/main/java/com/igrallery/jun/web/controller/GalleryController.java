package com.igrallery.jun.web.controller;

import com.igrallery.jun.domain.dto.GalleryForm;
import com.igrallery.jun.domain.entity.Gallery;
import com.igrallery.jun.domain.entity.User;
import com.igrallery.jun.domain.repository.GalleryRepository;
import com.igrallery.jun.domain.service.GalleryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;
    private final GalleryRepository galleryRepository;

    @PostMapping
    public String createGallery (@ModelAttribute @Valid GalleryForm form, User user) {
        return "redirect:/tool/" + galleryService.create(form, user);
    }

    @GetMapping("{id}")
    public String showGallery (@PathVariable Long id, Model model, HttpServletResponse response) throws IOException {
        Gallery gallery = getGallery(id);
        model.addAttribute("gallery", gallery);
        return "show";
    }

    @GetMapping("/delete/{gid}")
    public String deleteGallery (@PathVariable Long gid, User user, HttpServletRequest request) {
        galleryService.delete(user, gid);
        return "redirect:/profile/" + user.getEmail();
    }

    @GetMapping("/modify/{gid}")
    public String modifyGalleryPage (@PathVariable Long gid, User user, HttpServletRequest request, Model model) {
        Gallery gallery = galleryService.isValidThenGet(user, gid);
        model.addAttribute("gallery", gallery);
        return "modify";
    }

    @PostMapping("/modify/{gid}")
    public String modifyGallery (@PathVariable Long gid, User user, HttpServletRequest request) {
        return "redirect:/gallery/" + gid;
    }

    private Gallery getGallery (Long id) {
        return galleryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 갤러리는 존재하지 않습니다"));
    }
}
