package com.br.alumind.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public Info infoApi(){
        return new Info()
                .description("Uma API para avaliação de Feedbacks!")
                .title("AluMind service API REST")
                .version("V1.0.0");
    }

    @Bean
    public OpenAPI openApiInformation() {
        return new OpenAPI().info(infoApi());
    }
}



