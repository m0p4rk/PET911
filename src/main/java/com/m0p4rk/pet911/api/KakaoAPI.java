package com.m0p4rk.pet911.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;

@Controller
public class KakaoAPI {

    @Value("${api.key.kakao.rest}")
    private String kakaoRestApiKey;

    private final WebClient webClient;

    public KakaoAPI(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://apis-navi.kakaomobility.com").build();
    }

    @GetMapping("/api/kakao-key")
    @ResponseBody
    public String getKakaoApiKey() {
        return kakaoRestApiKey;
    }

    @GetMapping("/api/route")
    @ResponseBody
    public ResponseEntity<String> getRoute(@RequestParam double startX, @RequestParam double startY,
                                           @RequestParam double endX, @RequestParam double endY) {
        try {
            String response = this.webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/v1/directions")
                            .queryParam("origin", startX + "," + startY) // 경도, 위도 순서
                            .queryParam("destination", endX + "," + endY) // 경도, 위도 순서
                            .queryParam("priority", "RECOMMEND")
                            .queryParam("car_fuel", "GASOLINE")
                            .queryParam("car_hipass", "false")
                            .queryParam("alternatives", "false")
                            .queryParam("road_details", "false")
                            .build())
                    .header("Authorization", "KakaoAK " + kakaoRestApiKey)
                    .header("Content-Type", "application/json")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            e.printStackTrace(); // 서버 로그에 오류를 출력
            return ResponseEntity.status(e.getStatusCode()).body("Kakao API call failed: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace(); // 서버 로그에 오류를 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kakao API call failed: " + e.getMessage());
        }
    }
}
