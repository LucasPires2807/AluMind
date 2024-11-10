package com.br.alumind.repositories;
import com.br.alumind.models.FeedbackModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<FeedbackModel, UUID> {
}
