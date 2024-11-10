package com.br.alumind.services;

import com.br.alumind.utils.AnalysisResult;
import com.br.alumind.models.FeaturesModel;
import com.br.alumind.repositories.FeatureRepository;
import com.google.gson.Gson;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class OpenAiService {
   @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;
    @Autowired
    private OpenAiChatModel chatClient;
    @Autowired
    private FeatureRepository featureRepository;

    public String avaiableFeedback(String prompt){
        PromptTemplate promptTemplate = new PromptTemplate( "Esse feedback é positivo? responda com sim ou não" + prompt);
        String avaiable = this.chatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
        if(avaiable.toLowerCase().contains("sim")){
            return "positivo";
        }
        return "negativo";
    }

    public boolean avaiableSpamFeedback(String prompt){
        PromptTemplate promptTemplate = new PromptTemplate( "Estou desenvolvendo uma plataforma de estudos online chamada Alumind!" +
                "O conteudo desse feedback pode ser relacionado a Alumind? responda com sim ou não" + prompt);
        String validation_1 = this.chatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
        if(validation_1.contains("Não")) return true;

        promptTemplate = new PromptTemplate( "O conteudo desse feedback é agressivo? responda com sim ou não" + prompt);
        String validation_2 = this.chatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
        if(validation_2.contains("Sim")) return true;

        return false;
    }

    public FeaturesModel saveFeature(String prompt, UUID feedback_id){
        PromptTemplate promptTemplate = new PromptTemplate( "Esse feedback possui alguma sugestão? responda com sim ou não" + prompt);
        String feature = this.chatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
        if(feature.toLowerCase().contains("sim")){
            promptTemplate = new PromptTemplate("Estou avaliando os feedbacks recebidos na minha plataforma de estudos online chamada Alumind! " +
                    "Qual foi a sugestão feita pelo usuário? "+ prompt);
            String reason = this.chatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
            FeaturesModel featuresModel = new FeaturesModel();
            featuresModel.setReason(reason);
            featuresModel.setFeedbackId(feedback_id);
            return featureRepository.save(featuresModel);
        }
        return null;
    }

    public AnalysisResult analyzeFeedback(String prompt) {
        String fullPrompt = """
                Estou avaliando feedbacks para a plataforma de estudos online chamada Alumind. Analise o seguinte feedback e retorne uma string com o seguinte formato e informações:
                sentimento: "positivo" ou "negativo",
                "sugestao": true ou false,
                "funcionalidade": "descrição da funcionalidade sugerida, se houver"
                Feedback: %s
                """.formatted(prompt);

        String jsonResponse = chatClient.call(new PromptTemplate(fullPrompt).create())
                .getResult()
                .getOutput()
                .getContent();
        Gson gson = new Gson();
        AnalysisResult analysisResult = gson.fromJson("{"+jsonResponse+"}", AnalysisResult.class);
        return analysisResult;
    }

    public FeaturesModel saveFeatureFromAnalise(String reason, UUID feedback_id) {
        FeaturesModel featuresModel = new FeaturesModel();
        featuresModel.setReason("O usuário sugeriu " + reason);
        featuresModel.setFeedbackId(feedback_id);
        return featureRepository.save(featuresModel);
    }
}
