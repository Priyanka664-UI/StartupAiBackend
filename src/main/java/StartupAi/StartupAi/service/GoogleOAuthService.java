package StartupAi.StartupAi.service;

import StartupAi.StartupAi.dto.JwtResponse;
import StartupAi.StartupAi.model.AuthProvider;
import StartupAi.StartupAi.model.User;
import StartupAi.StartupAi.repository.UserRepository;
import StartupAi.StartupAi.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class GoogleOAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${google.client.id}")
    private String googleClientId;

    private final WebClient webClient;

    public GoogleOAuthService() {
        this.webClient = WebClient.builder().build();
    }

    public JwtResponse authenticateGoogleUser(String idTokenString) {
        System.out.println("Received Google ID token: " + idTokenString.substring(0, 50) + "...");
        
        try {
            // Verify the token with Google's tokeninfo endpoint
            String tokenInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idTokenString;
            System.out.println("Calling Google tokeninfo URL: " + tokenInfoUrl.substring(0, 80) + "...");
            
            Map<String, Object> tokenInfo = webClient.get()
                    .uri(tokenInfoUrl)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            System.out.println("Google tokeninfo response: " + tokenInfo);

            if (tokenInfo != null && tokenInfo.containsKey("aud") && 
                googleClientId.equals(tokenInfo.get("aud").toString())) {
                
                String email = tokenInfo.get("email").toString();
                String name = tokenInfo.get("name").toString();
                String googleId = tokenInfo.get("sub").toString();

                System.out.println("Checking if user exists: " + email);
                
                // Check if user exists in database
                Optional<User> existingUser = userRepository.findByEmail(email);
                
                if (existingUser.isPresent()) {
                    // User exists, proceed with login
                    User user = existingUser.get();
                    if (user.getProvider() == AuthProvider.LOCAL) {
                        // Update local user to also support Google login
                        user.setProvider(AuthProvider.GOOGLE);
                        user.setProviderId(googleId);
                        userRepository.save(user);
                    }
                    
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                        email, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
                    
                    String jwt = jwtUtils.generateJwtToken(authentication);
                    
                    System.out.println("Google authentication successful for existing user: " + email);
                    return new JwtResponse(jwt, user.getId(), user.getName(), user.getEmail());
                } else {
                    // User doesn't exist, throw exception to redirect to registration
                    System.out.println("User not found in database: " + email);
                    throw new RuntimeException("USER_NOT_REGISTERED:" + email + ":" + name + ":" + googleId);
                }
            } else {
                System.err.println("Token validation failed. Expected client ID: " + googleClientId);
                System.err.println("Received aud: " + (tokenInfo != null ? tokenInfo.get("aud") : "null"));
                throw new RuntimeException("Invalid Google ID token or client ID mismatch");
            }
        } catch (WebClientResponseException e) {
            System.err.println("Google API error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new RuntimeException("Failed to verify Google token: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Google authentication error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private User findOrCreateUser(String email, String name, String googleId) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.getProvider() == AuthProvider.LOCAL) {
                user.setProvider(AuthProvider.GOOGLE);
                user.setProviderId(googleId);
                return userRepository.save(user);
            }
            return user;
        } else {
            User newUser = new User(name, email, "", AuthProvider.GOOGLE, googleId);
            return userRepository.save(newUser);
        }
    }

    public JwtResponse registerGoogleUser(String email, String name, String googleId) {
        try {
            // Check if user already exists
            if (userRepository.existsByEmail(email)) {
                throw new RuntimeException("User already exists with this email");
            }
            
            // Create new Google user
            User newUser = new User(name, email, "", AuthProvider.GOOGLE, googleId);
            User savedUser = userRepository.save(newUser);
            
            // Generate JWT token
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                email, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
            
            String jwt = jwtUtils.generateJwtToken(authentication);
            
            System.out.println("Google user registered successfully: " + email);
            return new JwtResponse(jwt, savedUser.getId(), savedUser.getName(), savedUser.getEmail());
            
        } catch (Exception e) {
            System.err.println("Google user registration failed: " + e.getMessage());
            throw new RuntimeException("Failed to register Google user: " + e.getMessage());
        }
    }
}