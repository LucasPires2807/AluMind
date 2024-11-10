package com.br.alumind.utils;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnalysisResult {
    private String sentimento;
    private boolean sugestao;
    private String funcionalidade;
    @Override
    public String toString() {
        return "AnalysisResult{" +
                "sentimento='" + sentimento + '\'' +
                ", sugestao=" + sugestao +
                ", funcionalidade='" + funcionalidade + '\'' +
                '}';
    }
}
