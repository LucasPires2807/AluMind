package com.br.alumind.controllers;

import com.br.alumind.dtos.SentimentAnalysisDto;
import com.br.alumind.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("api/sentiment-analysis")
public class SentimentAnalysisController {

    @Autowired
    private SentimentAnalysisService service;

    /**
     * Endpoint to save a new sentiment analysis record.
     *
     * @param request SentimentAnalysisDto containing the input data
     * @return ResponseEntity with the created record or an error message
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody SentimentAnalysisDto request) {
        ResponseEntity<?> response;
        try {
            SentimentAnalysisDto savedDto = service.save(request);
            response = new ResponseEntity<>(savedDto, HttpStatus.CREATED);
        } catch (Exception e) {
            response = new ResponseEntity<>("Error while saving Sentiment Analysis: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Endpoint to retrieve all sentiment analysis records.
     *
     * @return ResponseEntity with a list of all records
     */
    @GetMapping
    public ResponseEntity<List<SentimentAnalysisDto>> findAll() {
        List<SentimentAnalysisDto> analysisList = service.findAll();
        return new ResponseEntity<>(analysisList, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a specific sentiment analysis record by ID.
     *
     * @param id UUID of the record
     * @return ResponseEntity with the record or an error message
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        ResponseEntity<?> response;
        SentimentAnalysisDto dto = service.findById(id);
        if (!Objects.isNull(dto)) {
            response = new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("Sentiment Analysis not found with the provided ID", HttpStatus.NOT_FOUND);
        }
        return response;
    }

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
