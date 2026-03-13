package StartupAi.StartupAi.service;

import StartupAi.StartupAi.agent.*;
import StartupAi.StartupAi.dto.IdeaGenerationRequest;
import StartupAi.StartupAi.dto.IdeaResponse;
import StartupAi.StartupAi.model.*;
import StartupAi.StartupAi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StartupIdeaService {
    
    @Autowired private StartupIdeaRepository startupIdeaRepository;
    @Autowired private MarketAnalysisRepository marketAnalysisRepository;
    @Autowired private CompetitorRepository competitorRepository;
    @Autowired private BusinessModelRepository businessModelRepository;
    @Autowired private RoadmapRepository roadmapRepository;
    
    @Autowired private IdeaGeneratorAgent ideaGeneratorAgent;
    @Autowired private TrendAnalysisAgent trendAnalysisAgent;
    @Autowired private MarketResearchAgent marketResearchAgent;
    @Autowired private CompetitorAnalysisAgent competitorAnalysisAgent;
    @Autowired private BusinessModelAgent businessModelAgent;
    @Autowired private RoadmapAgent roadmapAgent;
    @Autowired private OpportunityScoreAgent opportunityScoreAgent;
    
    @Transactional
    public IdeaResponse generateIdea(IdeaGenerationRequest request, Long userId) {
        // Step 1: Generate startup idea
        String ideaContent = ideaGeneratorAgent.generateIdea(request);
        
        // Parse the AI response (simplified for demo)
        String ideaTitle = "AI-Powered " + request.getIndustry() + " Solution";
        String problemStatement = "Current solutions in " + request.getIndustry() + " lack personalization and efficiency.";
        String solution = "Our AI-driven platform addresses these gaps with intelligent automation.";
        
        // Step 2: Create and save startup idea
        StartupIdea startupIdea = new StartupIdea();
        startupIdea.setUserId(userId);
        startupIdea.setIdeaTitle(ideaTitle);
        startupIdea.setProblemStatement(problemStatement);
        startupIdea.setSolution(solution);
        startupIdea.setIndustry(request.getIndustry());
        startupIdea = startupIdeaRepository.save(startupIdea);
        
        // Step 3: Run multi-agent analysis
        String trendAnalysis = trendAnalysisAgent.analyzeTrends(request.getIndustry());
        String marketResearch = marketResearchAgent.analyzeMarket(request.getIndustry());
        String competitorAnalysis = competitorAnalysisAgent.analyzeCompetitors(ideaTitle, request.getIndustry());
        String businessModel = businessModelAgent.generateBusinessModel(ideaTitle, request.getTargetAudience());
        String roadmap = roadmapAgent.generateRoadmap(ideaTitle);
        
        // Step 4: Calculate opportunity score
        Integer opportunityScore = opportunityScoreAgent.calculateScore(
            marketResearch, competitorAnalysis, trendAnalysis, "feasible"
        );
        
        startupIdea.setOpportunityScore(opportunityScore);
        startupIdeaRepository.save(startupIdea);
        
        // Step 5: Save detailed analysis
        saveMarketAnalysis(startupIdea.getId(), trendAnalysis, marketResearch);
        saveCompetitors(startupIdea.getId(), competitorAnalysis);
        saveBusinessModel(startupIdea.getId(), businessModel, request.getTargetAudience());
        saveRoadmap(startupIdea.getId(), roadmap);
        
        return convertToResponse(startupIdea);
    }
    
    public List<IdeaResponse> getUserIdeas(Long userId) {
        return startupIdeaRepository.findByUserId(userId)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    
    public IdeaResponse getIdeaDetails(Long ideaId) {
        StartupIdea idea = startupIdeaRepository.findById(ideaId)
            .orElseThrow(() -> new RuntimeException("Idea not found"));
        return convertToResponse(idea);
    }
    
    private void saveMarketAnalysis(Long ideaId, String trends, String research) {
        MarketAnalysis analysis = new MarketAnalysis();
        analysis.setIdeaId(ideaId);
        analysis.setIndustryTrends(trends);
        analysis.setMarketSize("$2.5B market with 15% annual growth");
        analysis.setInvestorInterest(research);
        marketAnalysisRepository.save(analysis);
    }
    
    private void saveCompetitors(Long ideaId, String analysis) {
        // Parse and save competitors (simplified)
        Competitor competitor = new Competitor();
        competitor.setIdeaId(ideaId);
        competitor.setCompetitorName("Market Leader Inc");
        competitor.setDescription("Leading solution in the market");
        competitor.setWebsite("https://competitor.com");
        competitorRepository.save(competitor);
    }
    
    private void saveBusinessModel(Long ideaId, String model, String targetAudience) {
        BusinessModel businessModel = new BusinessModel();
        businessModel.setIdeaId(ideaId);
        businessModel.setRevenueStream("Subscription-based SaaS model");
        businessModel.setPricingStrategy("Freemium with premium tiers");
        businessModel.setTargetCustomers(targetAudience);
        businessModelRepository.save(businessModel);
    }
    
    private void saveRoadmap(Long ideaId, String roadmapContent) {
        String[] steps = {"Market Research", "MVP Development", "Beta Testing", "Launch", "Scale"};
        for (int i = 0; i < steps.length; i++) {
            Roadmap roadmap = new Roadmap();
            roadmap.setIdeaId(ideaId);
            roadmap.setStepNumber(i + 1);
            roadmap.setStepDescription(steps[i]);
            roadmapRepository.save(roadmap);
        }
    }
    
    private IdeaResponse convertToResponse(StartupIdea idea) {
        IdeaResponse response = new IdeaResponse();
        response.setId(idea.getId());
        response.setIdeaTitle(idea.getIdeaTitle());
        response.setProblemStatement(idea.getProblemStatement());
        response.setSolution(idea.getSolution());
        response.setIndustry(idea.getIndustry());
        response.setOpportunityScore(idea.getOpportunityScore());
        return response;
    }
}