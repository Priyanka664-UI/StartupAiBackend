package StartupAi.StartupAi.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TechnologyRecommendationAgent {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public List<String> recommendTechnologies(String ideaTitle, String industry, String preferredTechnology) {
        String prompt = String.format(
            "Recommend technologies for building this startup. Startup Idea: '%s', Industry: %s, " +
            "Preferred Technology: %s. Provide specific recommendations for: " +
            "1) Frontend technologies (React, Angular, Flutter), 2) Backend technologies (Node.js, Python, Java), " +
            "3) Database solutions (PostgreSQL, MongoDB), 4) Cloud platforms (AWS, Azure, GCP), " +
            "5) Additional tools and frameworks. Consider scalability, cost-effectiveness, and development speed.",
            ideaTitle != null ? ideaTitle : "Innovative Solution", 
            industry != null ? industry : "Technology",
            preferredTechnology != null ? preferredTechnology : "No preference"
        );
        
        String response = aiAgentService.generateResponse(prompt);
        
        // Generate technology recommendations based on industry and preferences
        return generateTechStack(industry, preferredTechnology);
    }
    
    private List<String> generateTechStack(String industry, String preferredTechnology) {
        if (preferredTechnology != null && preferredTechnology.toLowerCase().contains("mobile")) {
            return Arrays.asList("React Native", "Flutter", "Firebase", "Node.js", "MongoDB");
        } else if (preferredTechnology != null && preferredTechnology.toLowerCase().contains("web")) {
            return Arrays.asList("React", "Node.js", "PostgreSQL", "AWS", "Docker");
        } else if (preferredTechnology != null && preferredTechnology.toLowerCase().contains("ai")) {
            return Arrays.asList("Python", "TensorFlow", "FastAPI", "PostgreSQL", "AWS SageMaker");
        } else {
            // Default recommendations based on industry
            return switch (industry != null ? industry.toLowerCase() : "general") {
                case "healthcare" -> Arrays.asList("React", "Node.js", "PostgreSQL", "HIPAA-compliant hosting", "Docker");
                case "fintech" -> Arrays.asList("React", "Java Spring Boot", "PostgreSQL", "AWS", "Kubernetes");
                case "edtech" -> Arrays.asList("React", "Node.js", "MongoDB", "AWS", "WebRTC");
                case "e-commerce" -> Arrays.asList("React", "Node.js", "PostgreSQL", "Stripe API", "AWS");
                default -> Arrays.asList("React", "Node.js", "PostgreSQL", "AWS", "Docker");
            };
        }
    }
}