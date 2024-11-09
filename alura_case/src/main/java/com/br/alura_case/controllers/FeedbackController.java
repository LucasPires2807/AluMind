package com.br.alura_case.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/feedback")
public class FeedbackController {
    private final FeedbackService service;
    private final FeedbackDTO dto;
    @PostMapping
    public ResponseEntity<FeedbackDTO> save(@RequestBody FeedbackDTO.RequestFeedback request){
        ResponseEntity response;
        FeedbackDTO responseFeedback = service.Save(request);
        if(!Objects.isNull(responseFeedback)){
            response = new ResponseEntity<>(responseFeedback, HttpStatus.OK);
        }else{
            response = new ResponseEntity<>("erro ao tentar salvar Feedback", HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> findAll(){
        List<FeedbackDTO> feedbacks = service.findAll();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id){
        ResponseEntity response;
        FeedbackDTO responseFeedback = service.findById(id);
        if(!Objects.isNull(responseFeedback)){
            response = new ResponseEntity<>(responseFeedback, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>("erro ao solicitar feedback com o id informado",HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping(value = "/{id}")
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
