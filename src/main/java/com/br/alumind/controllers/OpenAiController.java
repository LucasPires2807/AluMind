package com.br.alumind.controllers;

import com.br.alumind.dtos.FeedbackDto;
import com.br.alumind.services.FeedbackService;
import com.br.alumind.services.OpenAiService;
import com.br.alumind.utils.AnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/feedeback")
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

    @PostMapping("/person/{id}")
    public ResponseEntity<?> PersonFeedback(@RequestParam UUID id){
        String response = openAiService.generateResponseFeedback(id);
        if(Objects.nonNull(response)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }
}
