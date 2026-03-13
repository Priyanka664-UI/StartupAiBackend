package StartupAi.StartupAi.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarketResearchAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public String analyzeMarket(String industry) {
        String prompt = String.format(
            "Analyze the potential market size, investor interest, and industry growth for this startup idea. " +
            "Industry: %s. Provide detailed analysis on: 1) Total addressable market (TAM), " +
            "2) Serviceable addressable market (SAM), 3) Current investment trends and funding activity, " +
            "4) Market growth rate and projections, 5) Key market drivers and opportunities.",
            industry != null ? industry : "Technology"
        );
        return aiAgentService.generateResponse(prompt);
    }
}