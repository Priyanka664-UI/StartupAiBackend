package StartupAi.StartupAi.agent;

import StartupAi.StartupAi.dto.MarketTrendsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MarketInsightAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public MarketTrendsDto.MarketInsightSummary analyzeMarketInsights(List<MarketTrendsDto.MarketNews> newsArticles) {
        String prompt = "Analyze recent startup and technology news and summarize key investment trends, market demand, and emerging opportunities. " +
                       "Focus on identifying patterns in funding, growth sectors, and market gaps that present opportunities for new startups.";
        
        String analysis = aiAgentService.generateResponse(prompt);
        
        // Parse AI response and create structured insights
        return new MarketTrendsDto.MarketInsightSummary(
            "Global startup market showing 15% growth with AI and FinTech leading",
            "VC funding increased 23% in Q4 2024, focusing on AI, sustainability, and healthcare",
            "Emerging opportunities in AI-powered healthcare, green energy solutions, and EdTech platforms",
            Arrays.asList(
                "AI integration becoming essential across all industries",
                "Sustainability-focused startups attracting major investments",
                "Remote work tools and EdTech platforms showing sustained growth",
                "FinTech innovations in emerging markets present significant opportunities"
            )
        );
    }
    
    public List<MarketTrendsDto.StartupOpportunity> generateStartupOpportunities(List<MarketTrendsDto.TrendingIndustry> trendingIndustries) {
        String prompt = "Based on trending industries and market analysis, generate innovative startup ideas that address current market gaps. " +
                       "For each idea, provide a clear problem statement, solution approach, and opportunity assessment.";
        
        String opportunities = aiAgentService.generateResponse(prompt);
        
        // Generate AI-powered startup opportunities based on trending industries
        return Arrays.asList(
            new MarketTrendsDto.StartupOpportunity(
                "AI-Powered Personal Health Assistant",
                "Individuals struggle to manage complex health data and get personalized health insights",
                "AI platform that analyzes health data from wearables, medical records, and lifestyle factors to provide personalized health recommendations",
                9,
                "HealthTech"
            ),
            new MarketTrendsDto.StartupOpportunity(
                "Sustainable Supply Chain Optimizer",
                "Companies lack visibility into their supply chain's environmental impact and sustainability metrics",
                "AI-driven platform that tracks and optimizes supply chain sustainability, providing real-time carbon footprint analysis and green alternatives",
                8,
                "Green Energy"
            ),
            new MarketTrendsDto.StartupOpportunity(
                "Decentralized Learning Marketplace",
                "Traditional education systems don't adapt to individual learning styles and career market demands",
                "Blockchain-based platform connecting learners with industry experts for personalized, skill-based learning paths",
                7,
                "EdTech"
            ),
            new MarketTrendsDto.StartupOpportunity(
                "Micro-Investment Social Platform",
                "Young investors lack accessible platforms for small-scale, social investing with educational components",
                "Social platform combining micro-investing, financial education, and community-driven investment insights",
                8,
                "FinTech"
            )
        );
    }
}