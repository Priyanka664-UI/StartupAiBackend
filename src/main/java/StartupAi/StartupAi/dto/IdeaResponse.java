package StartupAi.StartupAi.dto;

import java.util.List;

public class IdeaResponse {
    private Long id;
    private String ideaTitle;
    private String problemStatement;
    private String solution;
    private String industry;
    private Integer opportunityScore;
    private MarketAnalysisDto marketAnalysis;
    private List<CompetitorDto> competitors;
    private List<BusinessModelDto> businessModels;
    private List<RoadmapDto> roadmaps;
    private List<String> suggestedTechnologies;

    // Constructors
    public IdeaResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public MarketAnalysisDto getMarketAnalysis() { return marketAnalysis; }
    public void setMarketAnalysis(MarketAnalysisDto marketAnalysis) { this.marketAnalysis = marketAnalysis; }

    public List<CompetitorDto> getCompetitors() { return competitors; }
    public void setCompetitors(List<CompetitorDto> competitors) { this.competitors = competitors; }

    public List<BusinessModelDto> getBusinessModels() { return businessModels; }
    public void setBusinessModels(List<BusinessModelDto> businessModels) { this.businessModels = businessModels; }

    public List<RoadmapDto> getRoadmaps() { return roadmaps; }
    public void setRoadmaps(List<RoadmapDto> roadmaps) { this.roadmaps = roadmaps; }

    public List<String> getSuggestedTechnologies() { return suggestedTechnologies; }
    public void setSuggestedTechnologies(List<String> suggestedTechnologies) { this.suggestedTechnologies = suggestedTechnologies; }
}