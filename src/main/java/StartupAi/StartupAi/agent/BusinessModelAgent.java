package StartupAi.StartupAi.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessModelAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public String generateBusinessModel(String ideaTitle, String targetAudience) {
        String prompt = String.format(
            "Suggest a sustainable business model including revenue streams, pricing strategy, and target customers. " +
            "Startup Idea: '%s', Target Audience: %s. Provide detailed recommendations on: " +
            "1) Primary and secondary revenue streams, 2) Pricing strategy and models (freemium, subscription, etc.), " +
            "3) Customer acquisition and retention strategies, 4) Cost structure and key partnerships, " +
            "5) Scalability and growth potential of the business model.",
            ideaTitle != null ? ideaTitle : "Innovative Solution", 
            targetAudience != null ? targetAudience : "General consumers"
        );
        return aiAgentService.generateResponse(prompt);
    }
}