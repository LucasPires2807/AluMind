package com.br.alumind.services;

import com.br.alumind.dtos.SentimentAnalysisDto;
import com.br.alumind.mappers.SentimentAnalysisMapper;
import com.br.alumind.models.SentimentAnalysisModel;
import com.br.alumind.repositories.SentimentAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SentimentAnalysisService {

    @Autowired
    private SentimentAnalysisRepository repository;

    @Autowired
    private SentimentAnalysisMapper mapper;

    /**
     * Retrieves all sentiment analysis records and converts them to DTOs.
     *
     * @return List of SentimentAnalysisDto
     */
    public List<SentimentAnalysisDto> findAll() {
        List<SentimentAnalysisModel> models = repository.findAll();
        return models.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a sentiment analysis record by ID.
     *
     * @param id the UUID of the record
     * @return SentimentAnalysisDto if found, otherwise null
     */
    public SentimentAnalysisDto findById(UUID id) {
        Optional<SentimentAnalysisModel> model = repository.findById(id);
        return model.map(mapper::toDTO).orElse(null);
    }

    /**
     * Creates a new sentiment analysis record based on the provided DTO.
     *
     * @param request the SentimentAnalysisDto to save
     * @return the created SentimentAnalysisDto
     */
    public SentimentAnalysisDto save(SentimentAnalysisDto request) {
        SentimentAnalysisModel model = mapper.toModel(request);
        SentimentAnalysisModel savedModel = repository.save(model);
        return mapper.toDTO(savedModel);
    }

    /**
     * Deletes a sentiment analysis record by ID.
     *
     * @param id the UUID of the record to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean delete(UUID id) {
        Optional<SentimentAnalysisModel> model = repository.findById(id);
        if (model.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
