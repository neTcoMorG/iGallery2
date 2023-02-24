package com.igrallery.jun.domain.dto;

import com.igrallery.jun.domain.entity.Gallery;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class GalleryModifyDto {

    private Gallery target;
    private User who;

    private Gallery modified;
}
