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
            "You are a startup innovation expert. Analyze the user's idea and refine it into a strong startup concept. " +
            "Provide a clear problem statement and solution. " +
            "Problem: %s, Skills: %s, Industry: %s, Target Audience: %s, Budget: %s, Technology: %s, Innovation Goal: %s. " +
            "Generate a refined startup idea with a catchy title, clear problem statement, and innovative solution.",
            request.getStartupProblem(), request.getUserSkills(), request.getIndustry(), 
            request.getTargetAudience(), request.getBudgetRange(), request.getPreferredTechnology(), request.getInnovationGoal()
        );
        return aiAgentService.generateResponse(prompt);
    }
}