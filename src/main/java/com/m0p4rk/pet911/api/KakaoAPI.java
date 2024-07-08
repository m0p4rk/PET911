package com.m0p4rk.pet911.api;

import com.m0p4rk.pet911.dto.UserSessionDTO;
import com.m0p4rk.pet911.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class KakaoAPI {

    private static final Logger logger = LoggerFactory.getLogger(KakaoAPI.class);

    @Value("${api.key.kakao.rest}")
    private String kakaoRestApiKey;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    private final WebClient webClient;
    private final UserService userService;

    @Autowired
    public KakaoAPI(WebClient.Builder webClientBuilder, UserService userService) {
        this.webClient = webClientBuilder.baseUrl("https://kauth.kakao.com").build();
        this.userService = userService;
    }

    @GetMapping("/auth/kakao")
    public String kakaoLogin() {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize" +
                "?client_id=" + kakaoRestApiKey +
                "&redirect_uri=" + kakaoRedirectUri +
                "&response_type=code";
        return "redirect:" + kakaoAuthUrl;
    }

    @GetMapping("/login/kakao/callback")
    public String kakaoCallback(@RequestParam String code, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String accessToken = getKakaoAccessToken(code);
            Map<String, Object> userInfo = getKakaoUserInfo(accessToken);
            logger.info("Kakao user info retrieved: {}", userInfo);

            UserSessionDTO userSessionDTO = processKakaoUser(userInfo);
            session.setAttribute("user", userSessionDTO);

            redirectAttributes.addFlashAttribute("message", "카카오 로그인 성공!");
            return "redirect:/";
        } catch (Exception e) {
            logger.error("Error during Kakao login process", e);
            redirectAttributes.addFlashAttribute("error", "로그인 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/login";
        }
    }

    private String getKakaoAccessToken(String code) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", kakaoRestApiKey)
                        .queryParam("redirect_uri", kakaoRedirectUri)
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(response -> (String) response.get("access_token"))
                .block();
    }

    private Map<String, Object> getKakaoUserInfo(String accessToken) {
        return WebClient.create("https://kapi.kakao.com")
                .get()
                .uri("/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private UserSessionDTO processKakaoUser(Map<String, Object> kakaoUserInfo) {
        Map<String, Object> properties = (Map<String, Object>) kakaoUserInfo.get("properties");
        Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoUserInfo.get("kakao_account");

        String email = (String) kakaoAccount.get("email");
        String nickname = (String) properties.get("nickname");

        return userService.findOrCreateKakaoUser(email, nickname);
    }
}