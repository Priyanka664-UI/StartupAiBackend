package StartupAi.StartupAi.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarketResearchAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public String analyzeMarket(String industry) {
        String prompt = String.format(
            "Analyze recent market news and summarize investment trends and market demand for %s industry.",
            industry
        );
        return aiAgentService.generateResponse(prompt);
    }
}