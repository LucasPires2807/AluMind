package com.br.alura_case.repositories;

import com.br.alura_case.models.FeedbackModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<FeedbackModel, UUID> {
}
