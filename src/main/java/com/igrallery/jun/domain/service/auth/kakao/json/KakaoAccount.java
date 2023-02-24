package com.igrallery.jun.domain.service.auth.kakao.json;

import lombok.Data;

@Data
public class KakaoAccount {
    private Profile profile;
    private String email;
}
