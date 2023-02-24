package com.igrallery.jun.domain.service.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@EnableConfigurationProperties
class FileServiceDiskImplTest {

    @Value("{file.thumbnail}") String thumbnailDir;

    @Test
    @DisplayName("썸네일 경로 생성 테스트")
    void getThumbnail_test () {
        //given
        String savedName = UUID.randomUUID().toString() + ".jpg";

        //when
        File file = new File(thumbnailDir + savedName);

        //then
        System.out.println(file.getPath());
    }



    String writerData = "str1,str2,str3,str4";
    String empty = "";
    MultipartFile real = new MockMultipartFile("real", "파일명.png", "text/plain", writerData.getBytes(StandardCharsets.UTF_8));
    MultipartFile fake = new MockMultipartFile("fake", "파일명.png", "text/plain", empty.getBytes(StandardCharsets.UTF_8));


    @Test
    @DisplayName("이미지 파일 유효성 테스트")
    void cutting_test () {
        //given
        List<MultipartFile> images = new ArrayList<>();
        images.add(real);
        images.add(fake);
        images.add(real);

        //when
        List<MultipartFile> result = new ArrayList<>();
        images.forEach(image -> {
            if (image.getSize() > 0) { result.add(image); }});

        //then
        assertEquals(result.size(), 2);
    }
}