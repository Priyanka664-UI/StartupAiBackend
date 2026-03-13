package StartupAi.StartupAi.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompetitorAnalysisAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public String analyzeCompetitors(String ideaTitle, String industry) {
        String prompt = String.format(
            "Identify startups or companies offering similar solutions to '%s' in %s industry and highlight gaps in the market.",
            ideaTitle, industry
        );
        return aiAgentService.generateResponse(prompt);
    }
}