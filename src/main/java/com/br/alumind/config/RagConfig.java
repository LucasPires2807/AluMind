package com.br.alumind.config;

import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagConfig {

   /* @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        // It's like you using H2 database for your application when making some demo
        // VectorStore is also the same case
        return new SimpleVectorStore(embeddingModel);
    } */

    @Bean
    public EmbeddingModel embeddingModel(OllamaApi ollamaClient) {
        OllamaOptions ollamaOptions = new OllamaOptions();
        ollamaOptions.setModel("llama3.2");
        return new OllamaEmbeddingModel(ollamaClient,ollamaOptions);
    }

}
