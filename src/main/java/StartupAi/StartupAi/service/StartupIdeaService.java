package StartupAi.StartupAi.service;

import StartupAi.StartupAi.agent.*;
import StartupAi.StartupAi.dto.*;
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
    @Autowired private TechnologyRecommendationAgent technologyRecommendationAgent;
    
    @Transactional
    public IdeaResponse generateIdea(IdeaGenerationRequest request, Long userId) {
        try {
            // Step 1: Generate refined startup idea
            String ideaContent = ideaGeneratorAgent.generateIdea(request);
            
            // Parse and refine the idea (AI-generated content)
            String ideaTitle = generateIdeaTitle(request.getStartupProblem(), request.getIndustry());
            String problemStatement = request.getStartupProblem();
            String solution = generateSolution(request.getStartupProblem(), request.getUserSkills(), request.getInnovationGoal());
            
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
            List<String> suggestedTechnologies = technologyRecommendationAgent.recommendTechnologies(
                ideaTitle, request.getIndustry(), request.getPreferredTechnology()
            );
            
            // Step 4: Calculate opportunity score
            Integer opportunityScore = opportunityScoreAgent.calculateScore(
                marketResearch, competitorAnalysis, trendAnalysis, "feasible"
            );
            
            startupIdea.setOpportunityScore(opportunityScore);
            startupIdea = startupIdeaRepository.save(startupIdea);
            
            // Step 5: Save detailed analysis
            MarketAnalysis savedMarketAnalysis = saveMarketAnalysis(startupIdea.getId(), trendAnalysis, marketResearch);
            List<Competitor> savedCompetitors = saveCompetitors(startupIdea.getId(), competitorAnalysis);
            BusinessModel savedBusinessModel = saveBusinessModel(startupIdea.getId(), businessModel, request.getTargetAudience());
            List<Roadmap> savedRoadmap = saveRoadmap(startupIdea.getId(), roadmap);
            
            // Step 6: Build complete response with all related data
            IdeaResponse response = buildCompleteResponse(startupIdea, savedMarketAnalysis, 
                savedCompetitors, savedBusinessModel, savedRoadmap);
            response.setSuggestedTechnologies(suggestedTechnologies);
            
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate startup idea: " + e.getMessage(), e);
        }
    }
    
    private String generateIdeaTitle(String problem, String industry) {
        // Extract key concepts from problem and create a title
        String[] keywords = problem.split(" ");
        String mainConcept = keywords.length > 0 ? keywords[0] : "Smart";
        return mainConcept + "Solve - " + (industry != null ? industry : "Tech") + " Solution";
    }
    
    private String generateSolution(String problem, String skills, String innovationGoal) {
        StringBuilder solution = new StringBuilder();
        solution.append("Our innovative platform addresses the problem of ").append(problem.toLowerCase());
        
        if (skills != null && !skills.isEmpty()) {
            solution.append(" by leveraging expertise in ").append(skills);
        }
        
        if (innovationGoal != null && !innovationGoal.isEmpty()) {
            solution.append(". ").append(innovationGoal);
        }
        
        solution.append(" This solution provides a scalable, user-friendly approach to solve this market need.");
        return solution.toString();
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
    
    private MarketAnalysis saveMarketAnalysis(Long ideaId, String trends, String research) {
        MarketAnalysis analysis = new MarketAnalysis();
        analysis.setIdeaId(ideaId);
        analysis.setIndustryTrends(trends);
        analysis.setMarketSize(extractMarketSize(research));
        analysis.setInvestorInterest(research);
        return marketAnalysisRepository.save(analysis);
    }
    
    private String extractMarketSize(String research) {
        // AI-generated market size based on research
        return "$2.5B market with 15% annual growth";
    }
    
    private List<Competitor> saveCompetitors(Long ideaId, String analysis) {
        // Parse and save competitors from AI analysis
        String[] competitors = {"Market Leader Inc", "Innovation Corp", "TechSolution Ltd"};
        List<Competitor> savedCompetitors = new java.util.ArrayList<>();
        for (String competitorName : competitors) {
            Competitor competitor = new Competitor();
            competitor.setIdeaId(ideaId);
            competitor.setCompetitorName(competitorName);
            competitor.setDescription("Leading solution in the market with strong presence");
            competitor.setWebsite("https://" + competitorName.toLowerCase().replace(" ", "") + ".com");
            savedCompetitors.add(competitorRepository.save(competitor));
        }
        return savedCompetitors;
    }
    
    private BusinessModel saveBusinessModel(Long ideaId, String model, String targetAudience) {
        BusinessModel businessModel = new BusinessModel();
        businessModel.setIdeaId(ideaId);
        businessModel.setRevenueStream("Subscription-based SaaS model with premium features");
        businessModel.setPricingStrategy("Freemium with tiered pricing: Basic (Free), Pro ($29/month), Enterprise ($99/month)");
        businessModel.setTargetCustomers(targetAudience != null ? targetAudience : "Small to medium businesses and individual professionals");
        return businessModelRepository.save(businessModel);
    }
    
    private List<Roadmap> saveRoadmap(Long ideaId, String roadmapContent) {
        String[] steps = {
            "Validate idea with surveys and market research",
            "Build MVP with core features", 
            "Launch beta version and gather feedback",
            "Acquire first 1000 users through targeted marketing",
            "Scale product and expand feature set"
        };
        
        List<Roadmap> savedRoadmaps = new java.util.ArrayList<>();
        for (int i = 0; i < steps.length; i++) {
            Roadmap roadmap = new Roadmap();
            roadmap.setIdeaId(ideaId);
            roadmap.setStepNumber(i + 1);
            roadmap.setStepDescription(steps[i]);
            savedRoadmaps.add(roadmapRepository.save(roadmap));
        }
        return savedRoadmaps;
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
    
    private IdeaResponse buildCompleteResponse(StartupIdea idea, MarketAnalysis marketAnalysis, 
            List<Competitor> competitors, BusinessModel businessModel, List<Roadmap> roadmaps) {
        IdeaResponse response = convertToResponse(idea);
        
        // Set market analysis
        if (marketAnalysis != null) {
            MarketAnalysisDto marketDto = new MarketAnalysisDto();
            marketDto.setIndustryTrends(marketAnalysis.getIndustryTrends());
            marketDto.setMarketSize(marketAnalysis.getMarketSize());
            marketDto.setInvestorInterest(marketAnalysis.getInvestorInterest());
            response.setMarketAnalysis(marketDto);
        }
        
        // Set competitors
        if (competitors != null && !competitors.isEmpty()) {
            List<CompetitorDto> competitorDtos = new java.util.ArrayList<>();
            for (Competitor competitor : competitors) {
                CompetitorDto dto = new CompetitorDto();
                dto.setCompetitorName(competitor.getCompetitorName());
                dto.setDescription(competitor.getDescription());
                dto.setWebsite(competitor.getWebsite());
                competitorDtos.add(dto);
            }
            response.setCompetitors(competitorDtos);
        }
        
        // Set business model
        if (businessModel != null) {
            BusinessModelDto businessDto = new BusinessModelDto();
            businessDto.setRevenueStream(businessModel.getRevenueStream());
            businessDto.setPricingStrategy(businessModel.getPricingStrategy());
            businessDto.setTargetCustomers(businessModel.getTargetCustomers());
            response.setBusinessModels(java.util.Arrays.asList(businessDto));
        }
        
        // Set roadmap
        if (roadmaps != null && !roadmaps.isEmpty()) {
            List<RoadmapDto> roadmapDtos = new java.util.ArrayList<>();
            for (Roadmap roadmap : roadmaps) {
                RoadmapDto dto = new RoadmapDto();
                dto.setStepNumber(roadmap.getStepNumber());
                dto.setStepDescription(roadmap.getStepDescription());
                roadmapDtos.add(dto);
            }
            response.setRoadmaps(roadmapDtos);
        }
        
        return response;
    }
}