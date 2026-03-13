package StartupAi.StartupAi.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoadmapAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public String generateRoadmap(String ideaTitle) {
        String prompt = String.format(
            "Create a step-by-step roadmap for launching '%s' startup from idea validation to scaling.",
            ideaTitle
        );
        return aiAgentService.generateResponse(prompt);
    }
}