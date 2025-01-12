package com.br.alumind.controllers;

import com.br.alumind.models.FeedbackModel;
import com.br.alumind.services.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.io.IOException;

@RestController("ollama")
public class OllamaController {

    @Autowired
    private OllamaService chatService;
    public record Response(String justify, String Sentiment){}

    @GetMapping("generate")
    public Map generate(@RequestParam(value = "message") String message) {
        FeedbackModel feedbackModel = chatService.runClassify(message);
        return Map.of("ollama", new Response(feedbackModel.getJustify(),
                feedbackModel.getSentiment()));
    }

    @GetMapping("/generateRAG")
    public Map generateRAG(@RequestParam(value = "message") String message) {
        try {
            return Map.of("ollama", chatService.runRAG(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
