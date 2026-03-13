package StartupAi.StartupAi.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompetitorAnalysisAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public String analyzeCompetitors(String ideaTitle, String industry) {
        String prompt = String.format(
            "Identify existing companies working on similar solutions and highlight opportunities where this idea can be improved. " +
            "Startup Idea: '%s', Industry: %s. Provide analysis on: 1) Direct competitors and their market share, " +
            "2) Indirect competitors and alternative solutions, 3) Competitor strengths and weaknesses, " +
            "4) Market gaps and differentiation opportunities, 5) Competitive advantages this startup could leverage.",
            ideaTitle != null ? ideaTitle : "Innovative Solution", 
            industry != null ? industry : "Technology"
        );
        return aiAgentService.generateResponse(prompt);
    }
}