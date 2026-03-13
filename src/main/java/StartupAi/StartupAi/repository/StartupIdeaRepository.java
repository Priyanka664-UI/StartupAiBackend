package StartupAi.StartupAi.repository;

import StartupAi.StartupAi.model.StartupIdea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StartupIdeaRepository extends JpaRepository<StartupIdea, Long> {
    List<StartupIdea> findByUserId(Long userId);
}