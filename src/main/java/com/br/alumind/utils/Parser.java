package com.br.alumind.utils;

import com.br.alumind.models.FeedbackModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {
    public static FeedbackModel parseFeedback(String response) {
        // Pré-processamento: Limpar texto de caracteres indesejados
        response = cleanResponse(response);
        System.out.println("Parsing feedback: " + response);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FeedbackModel feedbackModel = objectMapper.readValue(response, FeedbackModel.class);
            return new FeedbackModel().builder()
                        .feedback(feedbackModel.getFeedback())
                        .sentimento(feedbackModel.getSentimento())
                        .justificativa(feedbackModel.getJustificativa())
                        .build();
        } catch (JsonProcessingException e) {
            System.err.println("Não foi possível extrair todos os campos da resposta : " + e.getMessage());
            return null;
        }

    }

    private static String cleanResponse(String response) {
        // Remove quebras de linha e espaços extras
        response = response.replaceAll("\\s+", " ").trim();
        response = response.replaceAll("`", " ").trim();

        if (!response.startsWith("{")) {
            response = "{" + response;
        }
        if (!response.endsWith("}")) {
            response = response + "}";
        }
        return response.toLowerCase();
    }
}


