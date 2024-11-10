package com.br.alumind.controllers;

import com.br.alumind.dtos.FeedbackDto;
import com.br.alumind.services.FeedbackService;
import com.br.alumind.services.OpenAiService;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class FeedbackController {
    @Autowired
    private FeedbackService service;
    @Autowired
    private OpenAiService openAiService;
    private final FeedbackDto dto;

    public FeedbackController() {
        this.dto = new FeedbackDto();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody FeedbackDto.RequestFeedback request){
        ResponseEntity response;
        if(openAiService.avaiableSpamFeedback(request.feedback())) {
            return new ResponseEntity<>("Erro ao tentar salvar Feedback. Spam detectado!", HttpStatus.BAD_REQUEST);
        }
        FeedbackDto.ResponseFeedback responseFeedback = service.save(request);
        if(!Objects.isNull(responseFeedback)){
            response = new ResponseEntity<>(responseFeedback, HttpStatus.OK);
        }else{
            response = new ResponseEntity<>("Erro ao tentar salvar Feedback", HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @GetMapping("feedback")
    public ResponseEntity<List<FeedbackDto.ResponseFeedback>> findAll(){
        List<FeedbackDto.ResponseFeedback> feedbacks = service.findAll();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping(value = "feedback/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id){
        ResponseEntity response;
        FeedbackDto.ResponseFeedback responseFeedback = service.findById(id);
        if(!Objects.isNull(responseFeedback)){
            response = new ResponseEntity<>(responseFeedback, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>("erro ao solicitar feedback com o id informado",HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping(value = "feedback/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        ResponseEntity response;

        if(service.delete(id)){
            response = new ResponseEntity<>("Feedback deletado com sucesso!", HttpStatus.OK);
        }else {
            response = new ResponseEntity<>("Erro ao tentar deletar Feedback", HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
