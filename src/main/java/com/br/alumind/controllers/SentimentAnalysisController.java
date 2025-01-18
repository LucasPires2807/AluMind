package com.br.alumind.controllers;

import com.br.alumind.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/sentiment-analysis")
public class SentimentAnalysisController {

    @Autowired
    private SentimentAnalysisService service;

    /**
     * Endpoint to delete a sentiment analysis record by ID.
     *
     * @param id UUID of the record to delete
     * @return ResponseEntity with a success or error message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ResponseEntity<?> response;
        if (service.delete(id)) {
            response = new ResponseEntity<>("Sentiment Analysis deleted successfully!", HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("Error while deleting Sentiment Analysis. Record not found.", HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
