package com.igrallery.jun.domain.service;

import com.igrallery.jun.domain.dto.GalleryForm;
import com.igrallery.jun.domain.dto.GalleryModifyDto;
import com.igrallery.jun.domain.entity.Gallery;
import com.igrallery.jun.domain.entity.User;

import java.util.Optional;

public interface GalleryService {

    Long create (GalleryForm form, User user);

    void delete (User who, Long id);

    Gallery modify(GalleryModifyDto dto);

    Gallery isValidThenGet (User user, Long gid);
}
