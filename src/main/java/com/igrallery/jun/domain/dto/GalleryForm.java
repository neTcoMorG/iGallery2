package com.igrallery.jun.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GalleryForm {
//    @NotBlank private String name;
    @NotBlank private String sub;
}
