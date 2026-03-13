package StartupAi.StartupAi.service;

import StartupAi.StartupAi.agent.AIAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    
    @Autowired
    private AIAgentService aiAgentService;
    
    public String getMentorResponse(String question) {
        String mentorPrompt = String.format(
            "You are an experienced startup mentor and business advisor. " +
            "Provide practical, actionable advice for this startup question: %s. " +
            "Focus on real-world insights, market validation, and growth strategies.",
            question
        );
        
        return aiAgentService.generateResponse(mentorPrompt);
    }
}