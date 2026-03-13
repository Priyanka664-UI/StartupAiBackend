package StartupAi.StartupAi.service;

import StartupAi.StartupAi.agent.MarketInsightAgent;
import StartupAi.StartupAi.agent.TrendAnalysisAgent;
import StartupAi.StartupAi.dto.MarketTrendsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketTrendsService {
    
    @Autowired
    private TrendAnalysisAgent trendAnalysisAgent;
    
    @Autowired
    private MarketInsightAgent marketInsightAgent;
    
    @Autowired
    private ExternalApiService externalApiService;
    
    public List<MarketTrendsDto.TrendingIndustry> getTrendingIndustries() {
        return trendAnalysisAgent.analyzeTrendingIndustries();
    }
    
    public List<MarketTrendsDto.StartupOpportunity> getStartupOpportunities() {
        List<MarketTrendsDto.TrendingIndustry> trendingIndustries = getTrendingIndustries();
        return marketInsightAgent.generateStartupOpportunities(trendingIndustries);
    }
    
    public Map<String, Object> getMarketNewsWithInsights() {
        List<MarketTrendsDto.MarketNews> news = externalApiService.getMarketNews();
        MarketTrendsDto.MarketInsightSummary insights = marketInsightAgent.analyzeMarketInsights(news);
        
        Map<String, Object> result = new HashMap<>();
        result.put("news", news);
        result.put("insights", insights);
        
        return result;
    }
    
    public List<MarketTrendsDto.CompetitorInfo> getCompetitors(String industry) {
        return externalApiService.getCompetitors(industry);
    }
    
    public List<MarketTrendsDto.TechnologyTrend> getTrendingTechnologies() {
        return externalApiService.getTrendingTechnologies();
    }
    
    public Map<String, Object> getOpportunityScoreDashboard() {
        List<MarketTrendsDto.TrendingIndustry> industries = getTrendingIndustries();
        
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("industries", industries);
        dashboard.put("averageOpportunityScore", 
            industries.stream()
                .mapToInt(MarketTrendsDto.TrendingIndustry::getOpportunityScore)
                .average()
                .orElse(0.0)
        );
        dashboard.put("topOpportunity", 
            industries.stream()
                .max((i1, i2) -> Integer.compare(i1.getOpportunityScore(), i2.getOpportunityScore()))
                .orElse(null)
        );
        
        return dashboard;
    }
    
    public Map<String, Object> getComprehensiveMarketTrends() {
        Map<String, Object> trends = new HashMap<>();
        
        trends.put("trendingIndustries", getTrendingIndustries());
        trends.put("startupOpportunities", getStartupOpportunities());
        trends.put("marketNews", getMarketNewsWithInsights());
        trends.put("trendingTechnologies", getTrendingTechnologies());
        trends.put("opportunityDashboard", getOpportunityScoreDashboard());
        
        return trends;
    }
}