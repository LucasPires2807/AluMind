package com.br.alumind.repositories;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.alumind.models.SentimentAnalysisModel;

public interface SentimentAnalysisRepository extends JpaRepository<SentimentAnalysisModel, UUID> {
}
