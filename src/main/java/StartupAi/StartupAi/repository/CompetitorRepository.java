package StartupAi.StartupAi.repository;

import StartupAi.StartupAi.model.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompetitorRepository extends JpaRepository<Competitor, Long> {
    List<Competitor> findByIdeaId(Long ideaId);
}