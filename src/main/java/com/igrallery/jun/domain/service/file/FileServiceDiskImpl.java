package com.igrallery.jun.domain.service.file;

import com.igrallery.jun.domain.entity.Gallery;
import com.igrallery.jun.domain.entity.img.Image;
import com.igrallery.jun.domain.entity.img.ItemType;
import com.igrallery.jun.domain.repository.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceDiskImpl implements FileService {

    private final ImageRepository imageRepository;

    @Value("${file.img}") private String imgDir;
    @Value("${file.thumbnail}") private String thumbnailDir;

    /**
     * saveImages : 갤러리에 사진과 썸네일을 설정하는 메소드
     * 
     * <p>넘어온 파라미터들이 유요한지 확인하고 이미지와 썸네일을 데이터베이스에 저장합니다</p>
     * 
     * @param gallery
     * @param images
     * @param itemTypes
     * @param thumbnail
     * @param backgroundHEX
     */
    @Override
    @Transactional
    public void saveImages (Gallery gallery, List<MultipartFile> images, List<String> itemTypes, MultipartFile thumbnail, String backgroundHEX) {
        List<MultipartFile> safeImages = filter(images);

        if (isIllegal(safeImages, thumbnail, itemTypes))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        for (int i=0; i< safeImages.size(); i++) {
            if (isValid(safeImages.get(i), ItemType.valueOf(itemTypes.get(i)))) {
                saveImage(gallery, ItemType.valueOf(itemTypes.get(i)), safeImages.get(i));
            }
        }

        saveThumbnail(gallery, thumbnail);
    }


    private boolean isIllegal (List<MultipartFile> images, MultipartFile thumbnail, List<String> itemTypes) {
        return images.size() != itemTypes.size() || thumbnail.isEmpty();
    }

    private boolean isValid (MultipartFile multipartFile, ItemType itemType) {
        return !multipartFile.isEmpty() && itemType != null;
    }

    private void saveThumbnail (Gallery gallery, MultipartFile thumbnail) {
        File th = getThumbnail();
        try { thumbnail.transferTo(th); }
        catch (Exception e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST); }
        gallery.updateThumbnail(th.getPath());
    }

    private SaveInformation getSaveInformation (MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String ext = originalName.substring((originalName.lastIndexOf(".")));
        String savedName = uuid + ext;
        String fullPath = imgDir + savedName;

        return new SaveInformation(originalName, savedName, fullPath);
    }

    private void saveImage (Gallery gallery, ItemType type, MultipartFile image) {
        SaveInformation saveInfo = getSaveInformation(image);

        try {
            image.transferTo(new File(saveInfo.getFullPath()));
            Image img = new Image(gallery, ItemType.valueOf(String.valueOf(type)),
                    saveInfo.getOriginalName(), saveInfo.getSavedName(), saveInfo.getFullPath());
            img = imageRepository.save(img);
        }
        catch (Exception e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST); }
    }

    private File getThumbnail () {
        String savedName = UUID.randomUUID().toString() + ".jpg";
        return new File(thumbnailDir + savedName);
    }

    private List<MultipartFile> filter (List<MultipartFile> images) {
        List<MultipartFile> result = new ArrayList<>();
        images.forEach(image -> {
            if (image.getSize() > 0) { result.add(image); }});

        return result;
    }

    public void delete (Image image) {
        File file = new File(image.getPath());
        if (file.exists())
            file.delete();
    }

    @Getter
    @AllArgsConstructor
    static class SaveInformation {
        private String originalName;
        private String savedName;
        private String fullPath;
    }
}
