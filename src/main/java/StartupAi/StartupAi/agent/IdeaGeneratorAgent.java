package StartupAi.StartupAi.agent;

import StartupAi.StartupAi.dto.IdeaGenerationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdeaGeneratorAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public String generateIdea(IdeaGenerationRequest request) {
        String prompt = String.format(
            "You are a startup innovation expert. Generate a unique startup idea based on: " +
            "Skills: %s, Interests: %s, Industry: %s, Budget: %s, Target Audience: %s. " +
            "Provide the idea title, problem statement, and solution.",
            request.getSkills(), request.getInterests(), request.getIndustry(), 
            request.getBudget(), request.getTargetAudience()
        );
        return aiAgentService.generateResponse(prompt);
    }
}