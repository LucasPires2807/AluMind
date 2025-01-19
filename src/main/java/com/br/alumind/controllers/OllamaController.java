package com.br.alumind.controllers;
import com.br.alumind.dtos.FeedbackDto;
import com.br.alumind.models.FeedbackModel;
import com.br.alumind.services.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.io.IOException;

@RestController("api/ollama")
public class OllamaController {

    @Autowired
    private OllamaService chatService;
    public record Response(String justify, String Sentiment){}
    @PostMapping("generate")
    public Map generate(@RequestParam(value = "message") String message) {
        return Map.of("ollama", chatService.runClassify(message));
    }

    @PostMapping("/generateRAG")
    public Map generateRAG(@RequestParam(value = "message") String message) {
        try {
            return Map.of("ollama", chatService.runRAG(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
