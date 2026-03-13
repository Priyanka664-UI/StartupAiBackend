package StartupAi.StartupAi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "market_analysis")
public class MarketAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idea_id")
    private Long ideaId;

    @Column(name = "industry_trends", columnDefinition = "TEXT")
    private String industryTrends;

    @Column(name = "market_size", columnDefinition = "TEXT")
    private String marketSize;

    @Column(name = "investor_interest", columnDefinition = "TEXT")
    private String investorInterest;

    @OneToOne
    @JoinColumn(name = "idea_id", insertable = false, updatable = false)
    private StartupIdea startupIdea;

    // Constructors
    public MarketAnalysis() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdeaId() { return ideaId; }
    public void setIdeaId(Long ideaId) { this.ideaId = ideaId; }

    public String getIndustryTrends() { return industryTrends; }
    public void setIndustryTrends(String industryTrends) { this.industryTrends = industryTrends; }

    public String getMarketSize() { return marketSize; }
    public void setMarketSize(String marketSize) { this.marketSize = marketSize; }

    public String getInvestorInterest() { return investorInterest; }
    public void setInvestorInterest(String investorInterest) { this.investorInterest = investorInterest; }

    public StartupIdea getStartupIdea() { return startupIdea; }
    public void setStartupIdea(StartupIdea startupIdea) { this.startupIdea = startupIdea; }
}