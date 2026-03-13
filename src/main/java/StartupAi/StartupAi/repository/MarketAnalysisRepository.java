package StartupAi.StartupAi.repository;

import StartupAi.StartupAi.model.MarketAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketAnalysisRepository extends JpaRepository<MarketAnalysis, Long> {
    MarketAnalysis findByIdeaId(Long ideaId);
}