package com.igrallery.jun.domain.service.file;

import com.igrallery.jun.domain.entity.Gallery;
import com.igrallery.jun.domain.entity.img.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    void saveImages (Gallery gallery, List<MultipartFile> images, List<String> itemTypes, MultipartFile thumbnail, String backgroundHEX);
    void delete (Image image);
}
