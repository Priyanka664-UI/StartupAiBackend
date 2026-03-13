package StartupAi.StartupAi.controller;

import StartupAi.StartupAi.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MarketController {
    
    @Autowired
    private ExternalApiService externalApiService;
    
    @GetMapping("/market-analysis/test")
    public ResponseEntity<?> testMarketAnalysis() {
        Map<String, Object> testData = new HashMap<>();
        testData.put("status", "success");
        testData.put("message", "Market Analysis API is working!");
        testData.put("timestamp", java.time.LocalDateTime.now());
        return ResponseEntity.ok(testData);
    }
    
    @GetMapping("/market-analysis/{ideaId}")
    public ResponseEntity<?> getMarketAnalysis(@PathVariable Long ideaId, 
                                             @RequestParam String industry) {
        try {
            Map<String, Object> analysis = externalApiService.getMarketAnalysis(industry);
            return ResponseEntity.ok(analysis);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/competitors/{ideaId}")
    public ResponseEntity<?> getCompetitorAnalysis(@PathVariable Long ideaId,
                                                 @RequestParam String ideaTitle) {
        try {
            Map<String, Object> competitors = externalApiService.getCompetitorAnalysis(ideaTitle);
            return ResponseEntity.ok(competitors);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/trends/{industry}")
    public ResponseEntity<?> getTrendAnalysis(@PathVariable String industry) {
        try {
            Map<String, Object> trends = externalApiService.getTrendAnalysis(industry);
            return ResponseEntity.ok(trends);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/tech-stack/{ideaId}")
    public ResponseEntity<?> getTechStackSuggestions(@PathVariable Long ideaId,
                                                   @RequestParam String ideaTitle) {
        try {
            Map<String, Object> techStack = externalApiService.getTechStackSuggestions(ideaTitle);
            return ResponseEntity.ok(techStack);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}