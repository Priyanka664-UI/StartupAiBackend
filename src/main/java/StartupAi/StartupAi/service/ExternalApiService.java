package StartupAi.StartupAi.service;

import StartupAi.StartupAi.dto.MarketTrendsDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExternalApiService {
    
    @Value("${app.news.api-key:}")
    private String newsApiKey;
    
    @Value("${app.github.token:}")
    private String githubToken;
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    
    public ExternalApiService() {
        this.webClient = WebClient.create();
        this.objectMapper = new ObjectMapper();
    }
    
    public Map<String, Object> getMarketAnalysis(String industry) {
        Map<String, Object> analysis = new HashMap<>();
        
        try {
            // Only fetch if API key is configured
            if (newsApiKey != null && !newsApiKey.isEmpty() && !newsApiKey.equals("your_news_api_key")) {
                List<MarketTrendsDto.MarketNews> news = fetchNewsFromAPI(industry);
                
                if (!news.isEmpty()) {
                    analysis.put("marketSize", estimateMarketSizeFromIndustry(industry));
                    analysis.put("growthRate", String.format("%.1f%%", calculateGrowthRateFromNews(news)));
                    analysis.put("targetAudience", getTargetAudienceForIndustry(industry));
                    analysis.put("keyInsights", extractInsightsFromNews(news));
                    analysis.put("marketSegments", getIndustrySegments(industry));
                    analysis.put("recentNews", news.stream().limit(5).collect(Collectors.toList()));
                }
            } else {
                throw new RuntimeException("News API key not configured");
            }
            
        } catch (Exception e) {
            System.err.println("Error fetching market analysis: " + e.getMessage());
            throw new RuntimeException("Failed to fetch market analysis data: " + e.getMessage());
        }
        
        return analysis;
    }
    
    public Map<String, Object> getCompetitorAnalysis(String ideaTitle) {
        Map<String, Object> competitors = new HashMap<>();
        
        try {
            // For now, return empty data as competitor APIs are not configured
            competitors.put("message", "Competitor API integration pending");
            competitors.put("directCompetitors", new ArrayList<>());
            competitors.put("marketGaps", "Competitor data unavailable - API not configured");
            competitors.put("competitionLevel", "Unable to assess - requires competitor API");
            competitors.put("fundingTrends", Map.of("message", "Funding data unavailable"));
            
        } catch (Exception e) {
            System.err.println("Error in competitor analysis: " + e.getMessage());
            throw new RuntimeException("Failed to analyze competitors: " + e.getMessage());
        }
        
        return competitors;
    }
    
    public Map<String, Object> getTrendAnalysis(String industry) {
        Map<String, Object> trends = new HashMap<>();
        
        try {
            // Only fetch if GitHub token is configured
            if (githubToken != null && !githubToken.isEmpty() && !githubToken.equals("your_github_token")) {
                List<MarketTrendsDto.TechnologyTrend> techTrends = fetchTrendingReposFromGitHub(industry);
                
                if (!techTrends.isEmpty()) {
                    trends.put("emergingTrends", extractTrendsFromGitHub(techTrends, industry));
                    trends.put("marketDrivers", deriveMarketDrivers(industry));
                    trends.put("challenges", identifyIndustryChallenges(industry));
                    trends.put("trendingTechnologies", techTrends);
                    trends.put("growthScore", calculateTrendScore(techTrends));
                } else {
                    trends.put("message", "No trending technologies found");
                }
            } else {
                throw new RuntimeException("GitHub API token not configured");
            }
            
        } catch (Exception e) {
            System.err.println("Error fetching trend analysis: " + e.getMessage());
            throw new RuntimeException("Failed to fetch trend data: " + e.getMessage());
        }
        
        return trends;
    }
    
    public Map<String, Object> getTechStackSuggestions(String ideaTitle) {
        Map<String, Object> techStack = new HashMap<>();
        
        try {
            // Only fetch if GitHub token is configured
            if (githubToken != null && !githubToken.isEmpty() && !githubToken.equals("your_github_token")) {
                List<MarketTrendsDto.TechnologyTrend> trendingTech = fetchTrendingReposFromGitHub("startup");
                
                if (!trendingTech.isEmpty()) {
                    Map<String, List<String>> recommendations = categorizeTechnologiesFromGitHub(trendingTech);
                    Map<String, String> reasoning = generateReasoningFromData(recommendations, trendingTech);
                    
                    techStack.put("recommended", recommendations);
                    techStack.put("reasoning", reasoning);
                    techStack.put("trendingFrameworks", extractFrameworksFromRepos(trendingTech));
                } else {
                    techStack.put("message", "No technology data available");
                }
            } else {
                throw new RuntimeException("GitHub API token not configured");
            }
            
        } catch (Exception e) {
            System.err.println("Error fetching tech stack suggestions: " + e.getMessage());
            throw new RuntimeException("Failed to fetch technology data: " + e.getMessage());
        }
        
        return techStack;
    }
    
    // Market Trends API methods
    
    public List<MarketTrendsDto.MarketNews> getMarketNews() {
        try {
            if (newsApiKey != null && !newsApiKey.isEmpty() && !newsApiKey.equals("your_news_api_key")) {
                return fetchNewsFromAPI("startup technology market");
            }
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error fetching market news: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public List<MarketTrendsDto.CompetitorInfo> getCompetitors(String industry) {
        // Return empty list as competitor API is not configured
        return new ArrayList<>();
    }
    
    public List<MarketTrendsDto.TechnologyTrend> getTrendingTechnologies() {
        try {
            if (githubToken != null && !githubToken.isEmpty() && !githubToken.equals("your_github_token")) {
                return fetchTrendingReposFromGitHub("trending");
            }
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error fetching trending technologies: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Real API integration methods
    
    private List<MarketTrendsDto.MarketNews> fetchNewsFromAPI(String query) {
        try {
            String searchQuery = query.replace(" ", "+");
            String url = "https://newsdata.io/api/1/news?apikey=" + newsApiKey + "&q=" + searchQuery + "&language=en&category=business,technology";
            
            String response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
            
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode results = jsonNode.get("results");
            
            List<MarketTrendsDto.MarketNews> newsList = new ArrayList<>();
            if (results != null && results.isArray()) {
                for (JsonNode article : results) {
                    if (article.get("title") != null && article.get("link") != null) {
                        MarketTrendsDto.MarketNews news = new MarketTrendsDto.MarketNews(
                            article.get("title").asText(),
                            article.get("description") != null ? article.get("description").asText() : "",
                            article.get("source_id") != null ? article.get("source_id").asText() : "Unknown",
                            parseDateTime(article.get("pubDate") != null ? article.get("pubDate").asText() : ""),
                            article.get("link").asText()
                        );
                        newsList.add(news);
                    }
                }
            }
            
            return newsList.stream().limit(10).collect(Collectors.toList());
        } catch (WebClientResponseException e) {
            System.err.println("News API error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new RuntimeException("News API request failed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing news data: " + e.getMessage());
            throw new RuntimeException("Failed to parse news data: " + e.getMessage());
        }
    }
    
    private List<MarketTrendsDto.TechnologyTrend> fetchTrendingReposFromGitHub(String topic) {
        try {
            String query = topic.replace(" ", "+");
            String url = "https://api.github.com/search/repositories?q=" + query + "+created:>2024-01-01&sort=stars&order=desc&per_page=20";
            
            String response = webClient.get()
                .uri(url)
                .header("Authorization", "token " + githubToken)
                .header("Accept", "application/vnd.github.v3+json")
                .retrieve()
                .bodyToMono(String.class)
                .block();
            
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode items = jsonNode.get("items");
            
            List<MarketTrendsDto.TechnologyTrend> techTrends = new ArrayList<>();
            if (items != null && items.isArray()) {
                for (JsonNode item : items) {
                    if (item.get("name") != null && item.get("html_url") != null) {
                        MarketTrendsDto.TechnologyTrend trend = new MarketTrendsDto.TechnologyTrend(
                            item.get("name").asText(),
                            item.get("language") != null ? item.get("language").asText() : "Unknown",
                            item.get("stargazers_count") != null ? item.get("stargazers_count").asInt() : 0,
                            item.get("description") != null ? item.get("description").asText() : "",
                            item.get("html_url").asText()
                        );
                        techTrends.add(trend);
                    }
                }
            }
            
            return techTrends.stream().limit(15).collect(Collectors.toList());
        } catch (WebClientResponseException e) {
            System.err.println("GitHub API error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new RuntimeException("GitHub API request failed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing GitHub data: " + e.getMessage());
            throw new RuntimeException("Failed to parse GitHub data: " + e.getMessage());
        }
    }
    
    // Helper methods for data processing
    
    private double calculateGrowthRateFromNews(List<MarketTrendsDto.MarketNews> news) {
        if (news.isEmpty()) return 0.0;
        
        long positiveNews = news.stream()
            .filter(n -> {
                String title = n.getTitle().toLowerCase();
                return title.contains("growth") || title.contains("increase") || 
                       title.contains("surge") || title.contains("rise") ||
                       title.contains("funding") || title.contains("investment");
            })
            .count();
        
        return Math.min(30.0, (double) positiveNews / news.size() * 25 + 5);
    }
    
    private String estimateMarketSizeFromIndustry(String industry) {
        Map<String, String> knownSizes = Map.of(
            "AI", "$45.2B",
            "FinTech", "$312.5B",
            "HealthTech", "$189.3B",
            "EdTech", "$89.5B",
            "E-commerce", "$4.9T"
        );
        return knownSizes.getOrDefault(industry, "Market size data requires additional research");
    }
    
    private String getTargetAudienceForIndustry(String industry) {
        Map<String, String> audiences = Map.of(
            "AI", "Tech-savvy professionals and enterprises seeking automation",
            "FinTech", "Digital-native consumers and SMEs needing financial solutions",
            "HealthTech", "Healthcare providers and health-conscious consumers",
            "EdTech", "Students, educators, and educational institutions"
        );
        return audiences.getOrDefault(industry, "Target audience analysis requires market research");
    }
    
    private List<String> extractInsightsFromNews(List<MarketTrendsDto.MarketNews> news) {
        List<String> insights = new ArrayList<>();
        
        if (news.isEmpty()) {
            insights.add("No recent news data available for analysis");
            return insights;
        }
        
        // Analyze news content for real insights
        long aiMentions = news.stream().filter(n -> n.getTitle().toLowerCase().contains("ai")).count();
        long mobileMentions = news.stream().filter(n -> n.getTitle().toLowerCase().contains("mobile")).count();
        long fundingMentions = news.stream().filter(n -> n.getTitle().toLowerCase().contains("funding")).count();
        
        if (aiMentions > 0) {
            insights.add("AI integration mentioned in " + aiMentions + " recent news articles");
        }
        if (mobileMentions > 0) {
            insights.add("Mobile technology trends appearing in " + mobileMentions + " news stories");
        }
        if (fundingMentions > 0) {
            insights.add("Funding activity reported in " + fundingMentions + " recent articles");
        }
        
        if (insights.isEmpty()) {
            insights.add("Market analysis based on " + news.size() + " recent news articles");
        }
        
        return insights;
    }
    
    private List<Map<String, Object>> getIndustrySegments(String industry) {
        List<Map<String, Object>> segments = new ArrayList<>();
        
        switch (industry.toLowerCase()) {
            case "ai":
                segments.add(Map.of("name", "Enterprise AI", "size", "45%", "growth", "18%"));
                segments.add(Map.of("name", "Consumer AI", "size", "35%", "growth", "22%"));
                segments.add(Map.of("name", "AI Infrastructure", "size", "20%", "growth", "15%"));
                break;
            case "fintech":
                segments.add(Map.of("name", "Digital Payments", "size", "40%", "growth", "12%"));
                segments.add(Map.of("name", "Lending", "size", "30%", "growth", "16%"));
                segments.add(Map.of("name", "Wealth Management", "size", "30%", "growth", "14%"));
                break;
            default:
                segments.add(Map.of("name", "Enterprise", "size", "Research needed", "growth", "Research needed"));
                segments.add(Map.of("name", "Consumer", "size", "Research needed", "growth", "Research needed"));
        }
        
        return segments;
    }
    
    private List<String> extractTrendsFromGitHub(List<MarketTrendsDto.TechnologyTrend> techTrends, String industry) {
        if (techTrends.isEmpty()) {
            return Arrays.asList("No trending technologies found for " + industry);
        }
        
        return techTrends.stream()
            .map(trend -> trend.getTechnologyName() + " (" + trend.getProgrammingLanguage() + ")")
            .limit(5)
            .collect(Collectors.toList());
    }
    
    private List<String> deriveMarketDrivers(String industry) {
        return Arrays.asList(
            "Technology adoption acceleration",
            "Digital transformation initiatives",
            "Market demand shifts",
            "Investment activity increases"
        );
    }
    
    private List<String> identifyIndustryChallenges(String industry) {
        return Arrays.asList(
            "Regulatory compliance requirements",
            "Technology integration complexity",
            "Market competition intensity",
            "Talent acquisition challenges"
        );
    }
    
    private double calculateTrendScore(List<MarketTrendsDto.TechnologyTrend> techTrends) {
        if (techTrends.isEmpty()) return 0.0;
        
        double avgStars = techTrends.stream()
            .mapToInt(MarketTrendsDto.TechnologyTrend::getStars)
            .average()
            .orElse(0.0);
        
        return Math.min(10.0, Math.max(1.0, avgStars / 1000.0));
    }
    
    private Map<String, List<String>> categorizeTechnologiesFromGitHub(List<MarketTrendsDto.TechnologyTrend> trends) {
        Map<String, List<String>> categories = new HashMap<>();
        
        List<String> frontend = trends.stream()
            .filter(t -> "JavaScript".equals(t.getProgrammingLanguage()) || "TypeScript".equals(t.getProgrammingLanguage()))
            .map(MarketTrendsDto.TechnologyTrend::getTechnologyName)
            .limit(4)
            .collect(Collectors.toList());
        
        List<String> backend = trends.stream()
            .filter(t -> "Python".equals(t.getProgrammingLanguage()) || "Java".equals(t.getProgrammingLanguage()) || "Go".equals(t.getProgrammingLanguage()))
            .map(MarketTrendsDto.TechnologyTrend::getTechnologyName)
            .limit(4)
            .collect(Collectors.toList());
        
        // Add defaults if no data found
        if (frontend.isEmpty()) frontend = Arrays.asList("No JavaScript/TypeScript repos found");
        if (backend.isEmpty()) backend = Arrays.asList("No Python/Java/Go repos found");
        
        categories.put("frontend", frontend);
        categories.put("backend", backend);
        categories.put("database", Arrays.asList("PostgreSQL", "MongoDB", "Redis"));
        categories.put("cloud", Arrays.asList("AWS", "Google Cloud", "Azure"));
        categories.put("ai", Arrays.asList("TensorFlow", "PyTorch", "OpenAI"));
        
        return categories;
    }
    
    private Map<String, String> generateReasoningFromData(Map<String, List<String>> recommendations, List<MarketTrendsDto.TechnologyTrend> trends) {
        Map<String, String> reasoning = new HashMap<>();
        
        for (MarketTrendsDto.TechnologyTrend trend : trends.stream().limit(3).collect(Collectors.toList())) {
            reasoning.put(trend.getTechnologyName(), 
                "Popular on GitHub with " + trend.getStars() + " stars - " + 
                (trend.getDescription().isEmpty() ? "Trending technology" : trend.getDescription()));
        }
        
        if (reasoning.isEmpty()) {
            reasoning.put("General", "Technology recommendations based on current market trends");
        }
        
        return reasoning;
    }
    
    private List<String> extractFrameworksFromRepos(List<MarketTrendsDto.TechnologyTrend> trends) {
        List<String> frameworks = trends.stream()
            .map(MarketTrendsDto.TechnologyTrend::getTechnologyName)
            .filter(name -> name.toLowerCase().contains("framework") || 
                           name.toLowerCase().contains("lib") ||
                           name.toLowerCase().contains("js") ||
                           name.toLowerCase().contains("react") ||
                           name.toLowerCase().contains("vue"))
            .limit(5)
            .collect(Collectors.toList());
        
        if (frameworks.isEmpty()) {
            frameworks = trends.stream()
                .map(MarketTrendsDto.TechnologyTrend::getTechnologyName)
                .limit(5)
                .collect(Collectors.toList());
        }
        
        return frameworks;
    }
    
    private LocalDateTime parseDateTime(String dateStr) {
        try {
            if (dateStr != null && !dateStr.isEmpty()) {
                // Handle different date formats
                if (dateStr.length() >= 19) {
                    return LocalDateTime.parse(dateStr.substring(0, 19));
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing date: " + dateStr + " - " + e.getMessage());
        }
        return LocalDateTime.now();
    }
}