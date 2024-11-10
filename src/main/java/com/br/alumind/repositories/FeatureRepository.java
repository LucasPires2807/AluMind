package com.br.alumind.repositories;
import com.br.alumind.models.FeaturesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FeatureRepository extends JpaRepository<FeaturesModel, UUID> {
    public FeaturesModel findByFeedbackId(UUID feedback_id);
}
