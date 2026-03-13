package StartupAi.StartupAi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "startup_ideas")
public class StartupIdea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "idea_title")
    private String ideaTitle;

    @Column(name = "problem_statement", columnDefinition = "TEXT")
    private String problemStatement;

    @Column(columnDefinition = "TEXT")
    private String solution;

    private String industry;

    @Column(name = "opportunity_score")
    private Integer opportunityScore;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToOne(mappedBy = "startupIdea", cascade = CascadeType.ALL)
    private MarketAnalysis marketAnalysis;

    @OneToMany(mappedBy = "startupIdea", cascade = CascadeType.ALL)
    private List<Competitor> competitors;

    @OneToMany(mappedBy = "startupIdea", cascade = CascadeType.ALL)
    private List<BusinessModel> businessModels;

    @OneToMany(mappedBy = "startupIdea", cascade = CascadeType.ALL)
    private List<Roadmap> roadmaps;

    // Constructors
    public StartupIdea() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getIdeaTitle() { return ideaTitle; }
    public void setIdeaTitle(String ideaTitle) { this.ideaTitle = ideaTitle; }

    public String getProblemStatement() { return problemStatement; }
    public void setProblemStatement(String problemStatement) { this.problemStatement = problemStatement; }

    public String getSolution() { return solution; }
    public void setSolution(String solution) { this.solution = solution; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public Integer getOpportunityScore() { return opportunityScore; }
    public void setOpportunityScore(Integer opportunityScore) { this.opportunityScore = opportunityScore; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public MarketAnalysis getMarketAnalysis() { return marketAnalysis; }
    public void setMarketAnalysis(MarketAnalysis marketAnalysis) { this.marketAnalysis = marketAnalysis; }

    public List<Competitor> getCompetitors() { return competitors; }
    public void setCompetitors(List<Competitor> competitors) { this.competitors = competitors; }

    public List<BusinessModel> getBusinessModels() { return businessModels; }
    public void setBusinessModels(List<BusinessModel> businessModels) { this.businessModels = businessModels; }

    public List<Roadmap> getRoadmaps() { return roadmaps; }
    public void setRoadmaps(List<Roadmap> roadmaps) { this.roadmaps = roadmaps; }
}