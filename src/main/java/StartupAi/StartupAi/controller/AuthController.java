package StartupAi.StartupAi.controller;

import StartupAi.StartupAi.dto.LoginRequest;
import StartupAi.StartupAi.dto.RegisterRequest;
import StartupAi.StartupAi.dto.GoogleLoginRequest;
import StartupAi.StartupAi.dto.GoogleRegisterRequest;
import StartupAi.StartupAi.dto.JwtResponse;
import StartupAi.StartupAi.model.User;
import StartupAi.StartupAi.service.AuthService;
import StartupAi.StartupAi.service.GoogleOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private GoogleOAuthService googleOAuthService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("Received login request for: " + loginRequest.getEmail());
        
        try {
            JwtResponse response = authService.login(loginRequest);
            System.out.println("Login successful for: " + loginRequest.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Login failed for: " + loginRequest.getEmail() + " - " + e.getMessage());
            
            String errorMessage;
            if (e.getMessage().contains("Incorrect password")) {
                errorMessage = "Incorrect password";
            } else if (e.getMessage().contains("User not found with this email address")) {
                errorMessage = "User not found with this email address";
            } else if (e.getMessage().contains("Bad credentials")) {
                errorMessage = "Invalid email or password";
            } else if (e.getMessage().contains("Account is locked")) {
                errorMessage = "Account is locked";
            } else if (e.getMessage().contains("Account is disabled")) {
                errorMessage = "Account is disabled";
            } else {
                errorMessage = "Login failed. Please try again.";
            }
            
            return ResponseEntity.badRequest().body("Error: " + errorMessage);
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        System.out.println("Received registration request for: " + registerRequest.getEmail());
        System.out.println("Name: " + registerRequest.getName());
        System.out.println("Password length: " + (registerRequest.getPassword() != null ? registerRequest.getPassword().length() : "null"));
        
        try {
            String message = authService.register(registerRequest);
            System.out.println("Registration successful for: " + registerRequest.getEmail());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            System.err.println("Registration failed for: " + registerRequest.getEmail() + " - " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@Valid @RequestBody GoogleLoginRequest googleLoginRequest) {
        System.out.println("Received Google login request with token: " + 
            (googleLoginRequest.getIdToken() != null ? googleLoginRequest.getIdToken().substring(0, 50) + "..." : "null"));
        try {
            JwtResponse response = googleOAuthService.authenticateGoogleUser(googleLoginRequest.getIdToken());
            System.out.println("Google login successful, returning JWT response");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Google login failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PostMapping("/google/register")
    public ResponseEntity<?> googleRegister(@Valid @RequestBody GoogleRegisterRequest googleRegisterRequest) {
        System.out.println("Received Google registration request for: " + googleRegisterRequest.getEmail());
        try {
            JwtResponse response = googleOAuthService.registerGoogleUser(
                googleRegisterRequest.getEmail(), 
                googleRegisterRequest.getName(), 
                googleRegisterRequest.getGoogleId()
            );
            System.out.println("Google registration successful, returning JWT response");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Google registration failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        try {
            User user = authService.getUserProfile(authentication.getName());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}