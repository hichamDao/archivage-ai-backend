package com.archivage.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class OllamaController {

    @Value("${ngrok.url}")
    private String ngrokUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<String> forwardToOllama(@RequestBody String body) {
        try {
            // Envoie la requête reçue vers ton Ollama local (via ngrok)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    ngrokUrl + "/api/chat",
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur proxy : " + e.getMessage());
        }
    }
}
