package com.br.alumind.services;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class SentimentClassifierService {
    public String analyzeSentiment(String text) {
        try {
            // Criar o comando para executar o script Python
            List<String> command = new ArrayList<>();
            command.add("python3"); // ou "python" dependendo do ambiente
            command.add("predict.py");
            command.add(text);

            // Configurar o ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);

            // Executar o script Python
            Process process = processBuilder.start();

            // Capturar a saída do script Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            process.waitFor();
            return output.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao executar script Python.";
        }
    }
}
