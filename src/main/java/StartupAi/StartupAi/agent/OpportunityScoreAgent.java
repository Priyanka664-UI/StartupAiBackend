package StartupAi.StartupAi.agent;

import org.springframework.stereotype.Component;

@Component
public class OpportunityScoreAgent {
    
    public Integer calculateScore(String marketDemand, String competitionLevel, String innovation, String feasibility) {
        // Simple scoring algorithm - in production, use more sophisticated ML models
        int score = 5; // Base score
        
        if (marketDemand.toLowerCase().contains("high")) score += 2;
        if (competitionLevel.toLowerCase().contains("low")) score += 2;
        if (innovation.toLowerCase().contains("innovative")) score += 1;
        if (feasibility.toLowerCase().contains("feasible")) score += 1;
        
        return Math.min(score, 10); // Cap at 10
    }
}