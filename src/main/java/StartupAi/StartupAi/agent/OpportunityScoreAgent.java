package StartupAi.StartupAi.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpportunityScoreAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public Integer calculateScore(String marketResearch, String competitorAnalysis, String trendAnalysis, String feasibility) {
        String prompt = String.format(
            "Calculate an opportunity score from 1 to 10 based on the following factors: " +
            "Market Research: %s, Competitor Analysis: %s, Trend Analysis: %s, Feasibility: %s. " +
            "Consider: 1) Market demand and size, 2) Competition level and market gaps, " +
            "3) Innovation level and uniqueness, 4) Technical and business feasibility. " +
            "Provide a score from 1-10 where 10 represents the highest opportunity potential.",
            marketResearch, competitorAnalysis, trendAnalysis, feasibility
        );
        
        String scoreAnalysis = aiAgentService.generateResponse(prompt);
        
        // Enhanced scoring algorithm based on multiple factors
        int baseScore = 5; // Start with middle score
        
        // Market demand scoring
        if (marketResearch.toLowerCase().contains("high demand") || 
            marketResearch.toLowerCase().contains("growing market")) {
            baseScore += 2;
        } else if (marketResearch.toLowerCase().contains("moderate")) {
            baseScore += 1;
        }
        
        // Competition level scoring
        if (competitorAnalysis.toLowerCase().contains("low competition") || 
            competitorAnalysis.toLowerCase().contains("market gap")) {
            baseScore += 2;
        } else if (competitorAnalysis.toLowerCase().contains("moderate competition")) {
            baseScore += 1;
        } else if (competitorAnalysis.toLowerCase().contains("high competition")) {
            baseScore -= 1;
        }
        
        // Innovation and trend scoring
        if (trendAnalysis.toLowerCase().contains("emerging trend") || 
            trendAnalysis.toLowerCase().contains("innovative")) {
            baseScore += 1;
        }
        
        // Feasibility scoring
        if (feasibility.toLowerCase().contains("highly feasible")) {
            baseScore += 1;
        } else if (feasibility.toLowerCase().contains("challenging")) {
            baseScore -= 1;
        }
        
        // Ensure score is within 1-10 range
        return Math.min(Math.max(baseScore, 1), 10);
    }
}