package com.igrallery.jun.web.controller;

import com.igrallery.jun.domain.entity.Gallery;
import com.igrallery.jun.domain.repository.GalleryRepository;
import com.igrallery.jun.domain.service.GalleryService;
import com.igrallery.jun.domain.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/tool")
@RequiredArgsConstructor
public class ToolController {

    private final FileService fileService;
    private final GalleryService galleryService;

    @GetMapping("/{id}")
    public String toolPage (@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "tool";
    }

    @ResponseBody
    @PostMapping("/{id}")
    public String saveImages (@PathVariable(name = "id") Long gid,
                              @RequestPart List<String> items,
                              @RequestPart(required = false) String backgroundHEX,
                              @RequestPart List<MultipartFile> images,
                              @RequestPart MultipartFile thumbnail,
                              HttpServletResponse response) throws IOException {

        updateGallery(galleryService.get(gid), images, items, thumbnail, backgroundHEX);
        return "/gallery/" + gid;
    }


    private void updateGallery (Gallery gallery,
                               List<MultipartFile> images,
                               List<String> items,
                               MultipartFile thumbnail,
                               String hex) {

        gallery.updateBackgroundColor(hex);
        fileService.saveImages(gallery, images, items, thumbnail, hex);
    }
}
