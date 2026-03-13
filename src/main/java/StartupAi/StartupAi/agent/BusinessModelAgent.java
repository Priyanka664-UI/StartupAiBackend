package StartupAi.StartupAi.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessModelAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public String generateBusinessModel(String ideaTitle, String targetAudience) {
        String prompt = String.format(
            "Generate a sustainable business model for '%s' targeting %s including revenue streams, pricing strategy, and target customers.",
            ideaTitle, targetAudience
        );
        return aiAgentService.generateResponse(prompt);
    }
}