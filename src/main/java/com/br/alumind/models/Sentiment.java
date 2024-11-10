package com.br.alumind.models;


public enum Sentiment {
    NEGATIVO("NEGATIVO"),
    POSITIVO("POSITIVO"),
    INCONCLUSIVO("INCONCLUSIVO");

    private String description;

    Sentiment(String description) {
        this.description = description;
    }

    public static Sentiment convertToEnum(String str){
        switch (str){
            case "negativo": return NEGATIVO;
            case "positivo": return POSITIVO;
            default: return INCONCLUSIVO;
        }
    }
    @Override
    public String toString() {
        return this.description;
    }
}