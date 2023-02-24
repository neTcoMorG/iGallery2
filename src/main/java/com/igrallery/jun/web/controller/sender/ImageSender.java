package com.igrallery.jun.web.controller.sender;

import com.igrallery.jun.domain.repository.ImageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageSender {

    private final ImageRepository imageRepository;

    @Value("${file.img}") private String imgDir;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> send (@PathVariable String id) throws IOException {
        Resource image = getImage(id);

        if (image.exists()) {
            return ResponseEntity.ok()
                    .headers(createHeader(getImagePath(id)))
                    .body(image);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private HttpHeaders createHeader (Path path) throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", Files.probeContentType(path));
        return header;
    }

    private Resource getImage (String id) {
        return new FileSystemResource(imgDir + id);
    }

    private Path getImagePath (String id) {
        return Paths.get(imgDir + id);
    }
}

