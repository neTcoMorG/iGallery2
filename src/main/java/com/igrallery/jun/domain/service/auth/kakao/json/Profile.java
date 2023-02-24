package com.igrallery.jun.domain.service.auth.kakao.json;

import lombok.Data;

@Data
public class Profile {
    private String nickname;
    private String profile_image_url;
    private String thumbnail_image_url;
}
