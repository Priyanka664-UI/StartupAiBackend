package StartupAi.StartupAi.dto;

public class MarketAnalysisDto {
    private String industryTrends;
    private String marketSize;
    private String investorInterest;

    // Constructors and getters/setters
    public String getIndustryTrends() { return industryTrends; }
    public void setIndustryTrends(String industryTrends) { this.industryTrends = industryTrends; }

    public String getMarketSize() { return marketSize; }
    public void setMarketSize(String marketSize) { this.marketSize = marketSize; }

    public String getInvestorInterest() { return investorInterest; }
    public void setInvestorInterest(String investorInterest) { this.investorInterest = investorInterest; }
}