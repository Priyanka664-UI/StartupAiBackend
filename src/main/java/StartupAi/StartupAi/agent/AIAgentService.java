package StartupAi.StartupAi.agent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AIAgentService {
    
    @Value("${app.openai.api-key}")
    private String openaiApiKey;
    
    private final WebClient webClient;
    
    public AIAgentService() {
        this.webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .build();
    }
    
    public String generateResponse(String prompt) {
        try {
            // Simulate AI response for demo purposes
            // In production, integrate with actual OpenAI API
            return simulateAIResponse(prompt);
        } catch (Exception e) {
            return "Error generating AI response: " + e.getMessage();
        }
    }
    
    private String simulateAIResponse(String prompt) {
        if (prompt.contains("startup idea")) {
            return "AI-Powered Personal Finance Assistant - A mobile app that uses machine learning to analyze spending patterns and provide personalized financial advice.";
        } else if (prompt.contains("market trends")) {
            return "The fintech industry is experiencing 25% growth annually with increasing demand for AI-driven financial solutions.";
        } else if (prompt.contains("competitors")) {
            return "Main competitors include Mint, YNAB, and Personal Capital. Market gap exists in AI-powered personalized recommendations.";
        } else if (prompt.contains("business model")) {
            return "Freemium model with premium features at $9.99/month. Target customers: millennials and Gen Z with disposable income.";
        } else if (prompt.contains("roadmap")) {
            return "1. Market Research (Month 1-2), 2. MVP Development (Month 3-6), 3. Beta Testing (Month 7-8), 4. Launch (Month 9), 5. Scale (Month 10-12)";
        }
        return "AI response generated for: " + prompt;
    }
}