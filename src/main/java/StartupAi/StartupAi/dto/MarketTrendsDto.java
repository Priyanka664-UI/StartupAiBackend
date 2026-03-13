package StartupAi.StartupAi.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MarketTrendsDto {
    
    public static class TrendingIndustry {
        private String industryName;
        private Double trendScore;
        private Double growthPercentage;
        private String strongestRegion;
        private Integer opportunityScore;
        
        public TrendingIndustry() {}
        
        public TrendingIndustry(String industryName, Double trendScore, Double growthPercentage, String strongestRegion, Integer opportunityScore) {
            this.industryName = industryName;
            this.trendScore = trendScore;
            this.growthPercentage = growthPercentage;
            this.strongestRegion = strongestRegion;
            this.opportunityScore = opportunityScore;
        }
        
        // Getters and Setters
        public String getIndustryName() { return industryName; }
        public void setIndustryName(String industryName) { this.industryName = industryName; }
        public Double getTrendScore() { return trendScore; }
        public void setTrendScore(Double trendScore) { this.trendScore = trendScore; }
        public Double getGrowthPercentage() { return growthPercentage; }
        public void setGrowthPercentage(Double growthPercentage) { this.growthPercentage = growthPercentage; }
        public String getStrongestRegion() { return strongestRegion; }
        public void setStrongestRegion(String strongestRegion) { this.strongestRegion = strongestRegion; }
        public Integer getOpportunityScore() { return opportunityScore; }
        public void setOpportunityScore(Integer opportunityScore) { this.opportunityScore = opportunityScore; }
    }
    
    public static class StartupOpportunity {
        private String title;
        private String problemStatement;
        private String solution;
        private Integer opportunityScore;
        private String industry;
        
        public StartupOpportunity() {}
        
        public StartupOpportunity(String title, String problemStatement, String solution, Integer opportunityScore, String industry) {
            this.title = title;
            this.problemStatement = problemStatement;
            this.solution = solution;
            this.opportunityScore = opportunityScore;
            this.industry = industry;
        }
        
        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getProblemStatement() { return problemStatement; }
        public void setProblemStatement(String problemStatement) { this.problemStatement = problemStatement; }
        public String getSolution() { return solution; }
        public void setSolution(String solution) { this.solution = solution; }
        public Integer getOpportunityScore() { return opportunityScore; }
        public void setOpportunityScore(Integer opportunityScore) { this.opportunityScore = opportunityScore; }
        public String getIndustry() { return industry; }
        public void setIndustry(String industry) { this.industry = industry; }
    }
    
    public static class MarketNews {
        private String title;
        private String summary;
        private String source;
        private LocalDateTime publishedDate;
        private String url;
        
        public MarketNews() {}
        
        public MarketNews(String title, String summary, String source, LocalDateTime publishedDate, String url) {
            this.title = title;
            this.summary = summary;
            this.source = source;
            this.publishedDate = publishedDate;
            this.url = url;
        }
        
        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getSummary() { return summary; }
        public void setSummary(String summary) { this.summary = summary; }
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
        public LocalDateTime getPublishedDate() { return publishedDate; }
        public void setPublishedDate(LocalDateTime publishedDate) { this.publishedDate = publishedDate; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }
    
    public static class CompetitorInfo {
        private String startupName;
        private String industry;
        private String description;
        private String websiteUrl;
        private String fundingStage;
        
        public CompetitorInfo() {}
        
        public CompetitorInfo(String startupName, String industry, String description, String websiteUrl, String fundingStage) {
            this.startupName = startupName;
            this.industry = industry;
            this.description = description;
            this.websiteUrl = websiteUrl;
            this.fundingStage = fundingStage;
        }
        
        // Getters and Setters
        public String getStartupName() { return startupName; }
        public void setStartupName(String startupName) { this.startupName = startupName; }
        public String getIndustry() { return industry; }
        public void setIndustry(String industry) { this.industry = industry; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getWebsiteUrl() { return websiteUrl; }
        public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }
        public String getFundingStage() { return fundingStage; }
        public void setFundingStage(String fundingStage) { this.fundingStage = fundingStage; }
    }
    
    public static class TechnologyTrend {
        private String technologyName;
        private String programmingLanguage;
        private Integer stars;
        private String description;
        private String repositoryUrl;
        
        public TechnologyTrend() {}
        
        public TechnologyTrend(String technologyName, String programmingLanguage, Integer stars, String description, String repositoryUrl) {
            this.technologyName = technologyName;
            this.programmingLanguage = programmingLanguage;
            this.stars = stars;
            this.description = description;
            this.repositoryUrl = repositoryUrl;
        }
        
        // Getters and Setters
        public String getTechnologyName() { return technologyName; }
        public void setTechnologyName(String technologyName) { this.technologyName = technologyName; }
        public String getProgrammingLanguage() { return programmingLanguage; }
        public void setProgrammingLanguage(String programmingLanguage) { this.programmingLanguage = programmingLanguage; }
        public Integer getStars() { return stars; }
        public void setStars(Integer stars) { this.stars = stars; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getRepositoryUrl() { return repositoryUrl; }
        public void setRepositoryUrl(String repositoryUrl) { this.repositoryUrl = repositoryUrl; }
    }
    
    public static class MarketInsightSummary {
        private String marketGrowth;
        private String investmentTrends;
        private String emergingOpportunities;
        private List<String> keyInsights;
        
        public MarketInsightSummary() {}
        
        public MarketInsightSummary(String marketGrowth, String investmentTrends, String emergingOpportunities, List<String> keyInsights) {
            this.marketGrowth = marketGrowth;
            this.investmentTrends = investmentTrends;
            this.emergingOpportunities = emergingOpportunities;
            this.keyInsights = keyInsights;
        }
        
        // Getters and Setters
        public String getMarketGrowth() { return marketGrowth; }
        public void setMarketGrowth(String marketGrowth) { this.marketGrowth = marketGrowth; }
        public String getInvestmentTrends() { return investmentTrends; }
        public void setInvestmentTrends(String investmentTrends) { this.investmentTrends = investmentTrends; }
        public String getEmergingOpportunities() { return emergingOpportunities; }
        public void setEmergingOpportunities(String emergingOpportunities) { this.emergingOpportunities = emergingOpportunities; }
        public List<String> getKeyInsights() { return keyInsights; }
        public void setKeyInsights(List<String> keyInsights) { this.keyInsights = keyInsights; }
    }
}