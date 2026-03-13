package StartupAi.StartupAi.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoadmapAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public String generateRoadmap(String ideaTitle) {
        String prompt = String.format(
            "Create a step-by-step plan for building and launching this startup from idea validation to product launch. " +
            "Startup Idea: '%s'. Generate a comprehensive roadmap with: " +
            "1) Idea validation phase (surveys, market research), 2) MVP development and testing, " +
            "3) Beta launch and user feedback, 4) User acquisition and growth strategies, " +
            "5) Product scaling and feature expansion. Include timelines and key milestones for each step.",
            ideaTitle != null ? ideaTitle : "Innovative Startup"
        );
        return aiAgentService.generateResponse(prompt);
    }
}