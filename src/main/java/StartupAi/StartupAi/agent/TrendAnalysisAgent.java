package StartupAi.StartupAi.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrendAnalysisAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public String analyzeTrends(String industry) {
        String prompt = String.format(
            "Analyze trending technologies and industries related to %s and identify high-growth opportunities.",
            industry
        );
        return aiAgentService.generateResponse(prompt);
    }
}