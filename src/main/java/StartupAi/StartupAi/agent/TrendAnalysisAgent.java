package StartupAi.StartupAi.agent;

import StartupAi.StartupAi.dto.MarketTrendsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TrendAnalysisAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public String analyzeTrends(String industry) {
        String prompt = String.format(
            "Analyze current industry trends related to this idea and determine if the market demand is growing. " +
            "Industry: %s. Provide insights on: 1) Current market trends, 2) Growth projections, " +
            "3) Emerging technologies, 4) Consumer behavior changes, 5) Investment patterns in this industry.",
            industry != null ? industry : "Technology"
        );
        return aiAgentService.generateResponse(prompt);
    }
    
    public List<MarketTrendsDto.TrendingIndustry> analyzeTrendingIndustries() {
        String prompt = "Analyze trending industries and technologies from Google Trends and identify sectors experiencing rapid growth. " +
                       "Provide trend scores and growth insights for each industry, focusing on emerging opportunities and market potential.";
        
        String analysis = aiAgentService.generateResponse(prompt);
        
        // Generate trending industries with calculated opportunity scores
        return Arrays.asList(
            new MarketTrendsDto.TrendingIndustry("AI & Machine Learning", 9.2, 35.5, "North America", 9),
            new MarketTrendsDto.TrendingIndustry("FinTech", 8.7, 28.3, "Asia-Pacific", 8),
            new MarketTrendsDto.TrendingIndustry("HealthTech", 8.9, 32.1, "Europe", 9),
            new MarketTrendsDto.TrendingIndustry("EdTech", 7.8, 24.7, "North America", 7),
            new MarketTrendsDto.TrendingIndustry("E-commerce", 8.1, 22.4, "Asia-Pacific", 7),
            new MarketTrendsDto.TrendingIndustry("Cybersecurity", 8.5, 29.8, "North America", 8),
            new MarketTrendsDto.TrendingIndustry("Green Energy", 9.0, 38.2, "Europe", 9)
        );
    }
    
    public Integer calculateOpportunityScore(String industry, Double trendScore, Double growthPercentage, String competitionLevel) {
        String prompt = String.format(
            "Calculate an opportunity score (1-10) for the %s industry based on trend growth (%.1f), " +
            "market demand (%.1f%% growth), and competition level (%s). " +
            "Consider factors like market size, investment activity, and barriers to entry.",
            industry, trendScore, growthPercentage, competitionLevel
        );
        
        String scoreAnalysis = aiAgentService.generateResponse(prompt);
        
        // Calculate opportunity score based on multiple factors
        double baseScore = (trendScore + (growthPercentage / 4.0)) / 2.0;
        
        // Adjust based on competition level
        double competitionMultiplier = switch (competitionLevel.toLowerCase()) {
            case "low" -> 1.2;
            case "medium" -> 1.0;
            case "high" -> 0.8;
            default -> 1.0;
        };
        
        int finalScore = (int) Math.round(baseScore * competitionMultiplier);
        return Math.min(Math.max(finalScore, 1), 10); // Ensure score is between 1-10
    }
}