package com.m0p4rk.pet911.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AiConversationalAPI {
    @Value("${api.url.ai}")
    private String flaskApiUrl;

    private final RestTemplate restTemplate;

    public AiConversationalAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/ask")
    public ResponseEntity<?> askQuestion(@RequestBody Map<String, String> request) {
        String question = request.get("question");

        Map<String, String> flaskRequest = new HashMap<>();
        flaskRequest.put("question", question);

        System.out.println("Sending request to: " + flaskApiUrl + "/ask");

        ResponseEntity<Map> response = restTemplate.postForEntity(flaskApiUrl + "/ask", flaskRequest, Map.class);
        return ResponseEntity.ok(response.getBody());
    }
}
