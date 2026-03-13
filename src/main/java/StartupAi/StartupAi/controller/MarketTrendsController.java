package StartupAi.StartupAi.controller;

import StartupAi.StartupAi.service.MarketTrendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market-trends")
@CrossOrigin(origins = "*")
public class MarketTrendsController {
    
    @Autowired
    private MarketTrendsService marketTrendsService;
    
    @GetMapping("/industries")
    public ResponseEntity<?> getTrendingIndustries() {
        try {
            return ResponseEntity.ok(marketTrendsService.getTrendingIndustries());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching trending industries: " + e.getMessage());
        }
    }
    
    @GetMapping("/startup-opportunities")
    public ResponseEntity<?> getStartupOpportunities() {
        try {
            return ResponseEntity.ok(marketTrendsService.getStartupOpportunities());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching startup opportunities: " + e.getMessage());
        }
    }
    
    @GetMapping("/news")
    public ResponseEntity<?> getMarketNews() {
        try {
            return ResponseEntity.ok(marketTrendsService.getMarketNewsWithInsights());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching market news: " + e.getMessage());
        }
    }
    
    @GetMapping("/competitors")
    public ResponseEntity<?> getCompetitors(@RequestParam(defaultValue = "AI") String industry) {
        try {
            return ResponseEntity.ok(marketTrendsService.getCompetitors(industry));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching competitors: " + e.getMessage());
        }
    }
    
    @GetMapping("/tech")
    public ResponseEntity<?> getTrendingTechnologies() {
        try {
            return ResponseEntity.ok(marketTrendsService.getTrendingTechnologies());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching trending technologies: " + e.getMessage());
        }
    }
    
    @GetMapping("/opportunities")
    public ResponseEntity<?> getOpportunityScoreDashboard() {
        try {
            return ResponseEntity.ok(marketTrendsService.getOpportunityScoreDashboard());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching opportunity scores: " + e.getMessage());
        }
    }
    
    @GetMapping("/comprehensive")
    public ResponseEntity<?> getComprehensiveMarketTrends() {
        try {
            return ResponseEntity.ok(marketTrendsService.getComprehensiveMarketTrends());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching comprehensive market trends: " + e.getMessage());
        }
    }
}