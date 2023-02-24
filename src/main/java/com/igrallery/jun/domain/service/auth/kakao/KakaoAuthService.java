package com.igrallery.jun.domain.service.auth.kakao;

import com.igrallery.jun.domain.service.auth.kakao.json.OauthToken;
import com.igrallery.jun.domain.service.auth.kakao.json.UserProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KakaoAuthService {

    @Value("${oauth.kakao.client_id}") private String clientId;
    @Value("${oauth.kakao.secret_id}") private String secret;
    @Value("${oauth.kakao.redirect_url}") private String redirectUrl;

    public OauthToken getToken(String code) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUrl);
        params.add("code", code);
        params.add("client_secret", secret);

        return webClient.post()
                .uri("/oauth/token")
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), error -> Mono.error(RuntimeException::new))
                .bodyToMono(OauthToken.class)
                .block();
    }

    /**
     * getProfile : 카카오 사용자 정보 가져오는 메소드
     *
     * @param accessToken : 카카오 엑세스 토큰
     * @return UserProfile : 유저 정보가 담긴 객체
     */
    public UserProfile getProfile(String accessToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .build();

        return webClient.post()
                .uri("/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(UserProfile.class)
                .block();
    }
}
