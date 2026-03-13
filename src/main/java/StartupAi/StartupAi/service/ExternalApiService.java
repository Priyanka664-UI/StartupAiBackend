package StartupAi.StartupAi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExternalApiService {
    
    @Value("${app.news.api-key}")
    private String newsApiKey;
    
    @Value("${app.serp.api-key}")
    private String serpApiKey;
    
    @Value("${app.github.token}")
    private String githubToken;
    
    private final WebClient webClient;
    
    public ExternalApiService() {
        this.webClient = WebClient.builder().build();
    }
    
    public Map<String, Object> getMarketAnalysis(String industry) {
        Map<String, Object> analysis = new HashMap<>();
        
        // Simulate News API call
        analysis.put("industryTrends", "AI and automation trends are growing at 25% annually in " + industry);
        analysis.put("investmentInsights", "VCs invested $2.1B in " + industry + " startups in 2024");
        analysis.put("marketOpportunity", "High growth potential with emerging market gaps");
        
        return analysis;
    }
    
    public Map<String, Object> getCompetitorAnalysis(String ideaTitle) {
        Map<String, Object> competitors = new HashMap<>();
        
        // Simulate SerpAPI call
        competitors.put("mainCompetitors", new String[]{"TechCorp Inc", "InnovateLab", "StartupX"});
        competitors.put("marketGaps", "Limited AI integration and poor user experience");
        competitors.put("competitionLevel", "Medium - opportunity for differentiation");
        
        return competitors;
    }
    
    public Map<String, Object> getTrendAnalysis(String industry) {
        Map<String, Object> trends = new HashMap<>();
        
        // Simulate Google Trends API
        trends.put("trendingKeywords", new String[]{"AI automation", "machine learning", "digital transformation"});
        trends.put("growthScore", 8.5);
        trends.put("topRegions", new String[]{"North America", "Europe", "Asia-Pacific"});
        
        return trends;
    }
    
    public Map<String, Object> getTechStackSuggestions(String ideaTitle) {
        Map<String, Object> techStack = new HashMap<>();
        
        // Simulate GitHub API call
        techStack.put("recommendedFrameworks", new String[]{"React", "Node.js", "Python", "TensorFlow"});
        techStack.put("trendingTools", new String[]{"Docker", "Kubernetes", "AWS", "MongoDB"});
        techStack.put("programmingLanguages", new String[]{"JavaScript", "Python", "Java"});
        
        return techStack;
    }
}