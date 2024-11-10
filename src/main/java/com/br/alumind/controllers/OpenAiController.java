package com.br.alumind.controllers;

import com.br.alumind.dtos.FeedbackDto;
import com.br.alumind.services.FeedbackService;
import com.br.alumind.services.OpenAiService;
import com.br.alumind.utils.AnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/ai")
public class OpenAiController {
    @Autowired
    OpenAiService openAiService;
    @Autowired
    FeedbackService feedbackService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody FeedbackDto.RequestFeedback request){
        AnalysisResult analyze = openAiService.analyzeFeedback(request.feedback());
        if(!openAiService.avaiableSpamFeedback(request.feedback())) {
            FeedbackDto.ResponseFeedback responseFeedback = feedbackService.saveFromAnalize(analyze, request.feedback());
            if(!Objects.isNull(responseFeedback)){
                return new ResponseEntity<>(responseFeedback, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Erro ao tentar salvar Feedback", HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>("Erro ao tentar salvar Feedback. Spam detectado!", HttpStatus.BAD_REQUEST);
    }
}
