package com.br.alumind.runner;

import com.br.alumind.dtos.SentimentAnalysisDto;
import com.br.alumind.repositories.FeedbackRepository;
import com.br.alumind.services.SentimentAnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(value = {"analysis"}, havingValue = "true")
public class AnalysisRunner implements ApplicationRunner {
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    SentimentAnalysisService sentimentAnalysisService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        feedbackRepository.findAll().forEach(feedback -> {
            SentimentAnalysisDto analysisDto = sentimentAnalysisService.saveAnalysis(feedback);
            if (Objects.nonNull(analysisDto)) log.info("Analysis result: ", analysisDto.toString());
        });
    }
}
