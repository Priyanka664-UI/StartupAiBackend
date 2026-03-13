package StartupAi.StartupAi.controller;

import StartupAi.StartupAi.dto.IdeaGenerationRequest;
import StartupAi.StartupAi.dto.IdeaResponse;
import StartupAi.StartupAi.model.User;
import StartupAi.StartupAi.repository.UserRepository;
import StartupAi.StartupAi.service.StartupIdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ideas")
@CrossOrigin(origins = "*")
public class IdeaController {
    
    @Autowired
    private StartupIdeaService startupIdeaService;
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/generate")
    public ResponseEntity<?> generateIdea(@Valid @RequestBody IdeaGenerationRequest request, 
                                        Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
            IdeaResponse response = startupIdeaService.generateIdea(request, user.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getUserIdeas(Authentication authentication) {
        try {
            User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
            List<IdeaResponse> ideas = startupIdeaService.getUserIdeas(user.getId());
            return ResponseEntity.ok(ideas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getIdeaDetails(@PathVariable Long id) {
        try {
            IdeaResponse idea = startupIdeaService.getIdeaDetails(id);
            return ResponseEntity.ok(idea);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}