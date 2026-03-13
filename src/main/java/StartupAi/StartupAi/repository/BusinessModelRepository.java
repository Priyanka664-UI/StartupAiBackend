package StartupAi.StartupAi.repository;

import StartupAi.StartupAi.model.BusinessModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BusinessModelRepository extends JpaRepository<BusinessModel, Long> {
    List<BusinessModel> findByIdeaId(Long ideaId);
}