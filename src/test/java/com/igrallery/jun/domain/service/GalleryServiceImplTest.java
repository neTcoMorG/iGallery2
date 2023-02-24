package com.igrallery.jun.domain.service;

import com.igrallery.jun.domain.entity.Gallery;
import com.igrallery.jun.domain.entity.User;
import com.igrallery.jun.domain.repository.GalleryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class GalleryServiceImplTest {

    @Mock
    private GalleryRepository galleryRepository;

    @InjectMocks
    private GalleryServiceImpl galleryService;

    @Test
    @DisplayName("갤러리 삭제시 인가 확인")
    void deleteAuth_test() {
        //given
        User mockUserOwner = new User("a", "b", "c");
        User mockUserHacker = new User("b", "b", "c");
        Optional<Gallery> mockGallery = Optional.of(new Gallery(mockUserOwner, "asdsd"));

        given(galleryRepository.findById(anyLong())).willReturn(mockGallery);

        //when
        galleryService.delete(mockUserOwner, 2L);

        //then
        assertThrows(RuntimeException.class,
                () -> galleryService.delete(mockUserHacker, 1L));
    }
}