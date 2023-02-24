package com.igrallery.jun.web.controller.sender;

import com.igrallery.jun.domain.entity.Gallery;
import com.igrallery.jun.domain.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/thumbnail")
@RequiredArgsConstructor
public class ThumbnailSender {

    private final GalleryRepository galleryRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> sendThumbnail (@PathVariable Long id) {
        Gallery findGallery = getGallery(id);
        Resource thumbnail = getThumbnail(findGallery);

        return ResponseEntity.ok().headers(createResponseHeader(thumbnail)).body(thumbnail);
    }

    private Gallery getGallery (Long id) {
        return galleryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 갤러리 썸네일은 존재하지 않습니다"));
    }

    private Resource getThumbnail (Gallery gallery) {
        return new FileSystemResource(gallery.getThumbnail());
    }

    private HttpHeaders createResponseHeader (Resource thumbnail) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + thumbnail.getFilename() + "\"");
        return headers;
    }
}
