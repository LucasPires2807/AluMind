package com.br.alumind.controllers;

import com.br.alumind.services.GiminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class GiminiController {
    private final GiminiService textGenerationService;
    @Autowired
    public GiminiController(GiminiService textGenerationService) {
        this.textGenerationService = textGenerationService;
    }

    @PostMapping("/gimini")
    public String generateText(@RequestBody String prompt) {
        return textGenerationService.generateText(prompt);
    }
}
