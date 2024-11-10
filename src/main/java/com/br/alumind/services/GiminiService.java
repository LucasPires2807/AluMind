package com.br.alumind.services;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GiminiService {

    @Value("${google.api.key}")
    private String apiKey;

    @Value("${google.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateText(String prompt) {
        // Configurar cabeçalhos HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Criar o corpo da requisição
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("prompt", prompt);
        requestBody.addProperty("temperature", 0.7);
        requestBody.addProperty("maxOutputTokens", 100);

        // Enviar a requisição HTTP
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

    public String saveFeatureFeedback(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        //melhorar pergunta
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("prompt", "Analise o seguinte feedback e identifique o assunto principal: " + "");
        requestBody.addProperty("temperature", 0.7);
        requestBody.addProperty("maxOutputTokens", 50);
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl+ "?key=" + apiKey, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

}
