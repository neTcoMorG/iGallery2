package com.igrallery.jun.domain.service;

import com.igrallery.jun.domain.dto.GalleryForm;
import com.igrallery.jun.domain.dto.GalleryModifyDto;
import com.igrallery.jun.domain.entity.Gallery;
import com.igrallery.jun.domain.entity.User;
import com.igrallery.jun.domain.entity.img.Image;
import com.igrallery.jun.domain.exception.GalleryNotFoundException;
import com.igrallery.jun.domain.exception.PermissionsException;
import com.igrallery.jun.domain.repository.GalleryRepository;
import com.igrallery.jun.domain.repository.ImageRepository;
import com.igrallery.jun.domain.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.File;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final ImageRepository imageRepository;
    private final FileService fileService;

    @Override
    public Long create (GalleryForm form, User user) {
        return galleryRepository.save(new Gallery(user, form.getSub())).getId();
    }

    @Override
    @Transactional
    public void delete (User user, Long gid) {
        Gallery gallery = get(gid);

        if (!gallery.isOwner(user))
            throw new PermissionsException("잘못된 접근");

        delete(gallery);
    }

    @Override
    @Transactional
    public Gallery modify (GalleryModifyDto dto) {
        return null;
    }

    public Gallery get (Long id) {
        return galleryRepository.findById(id).orElseThrow(
                GalleryNotFoundException::new);}

    private void permanentDeleteImage (Image image) {
        fileService.delete(image);
        imageRepository.delete(image);
    }

    private void permanentDeleteThumbnail (Gallery gallery) {
        File file = new File(gallery.getThumbnail());
        if (file.exists()) file.delete();
    }

    private void delete (Gallery gallery) {
        permanentDeleteThumbnail(gallery);
        gallery.getImages().forEach(this::permanentDeleteImage);
        galleryRepository.delete(gallery);
    }
}