package StartupAi.StartupAi.service;

import StartupAi.StartupAi.dto.LoginRequest;
import StartupAi.StartupAi.dto.RegisterRequest;
import StartupAi.StartupAi.dto.JwtResponse;
import StartupAi.StartupAi.model.User;
import StartupAi.StartupAi.model.AuthProvider;
import StartupAi.StartupAi.repository.UserRepository;
import StartupAi.StartupAi.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    public JwtResponse login(LoginRequest loginRequest) {
        System.out.println("Attempting login for user: " + loginRequest.getEmail());
        
        try {
            // Check if user exists first
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            if (userOptional.isEmpty()) {
                System.err.println("User not found: " + loginRequest.getEmail());
                throw new RuntimeException("User not found with this email address");
            }
            
            User user = userOptional.get();
            System.out.println("User found: " + user.getEmail() + ", attempting authentication...");
            
            try {
                Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
                );
                
                String jwt = jwtUtils.generateJwtToken(authentication);
                
                System.out.println("Login successful for user: " + user.getEmail() + " with ID: " + user.getId());
                return new JwtResponse(jwt, user.getId(), user.getName(), user.getEmail());
                
            } catch (org.springframework.security.authentication.BadCredentialsException e) {
                System.err.println("Incorrect password for user: " + loginRequest.getEmail());
                throw new RuntimeException("Incorrect password");
            }
            
        } catch (org.springframework.security.authentication.DisabledException e) {
            System.err.println("Account disabled for user: " + loginRequest.getEmail());
            throw new RuntimeException("Account is disabled");
        } catch (org.springframework.security.authentication.LockedException e) {
            System.err.println("Account locked for user: " + loginRequest.getEmail());
            throw new RuntimeException("Account is locked");
        } catch (Exception e) {
            System.err.println("Login failed for user: " + loginRequest.getEmail() + " - " + e.getMessage());
            throw e;
        }
    }
    
    public String register(RegisterRequest registerRequest) {
        System.out.println("Attempting to register user: " + registerRequest.getEmail());
        
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            System.err.println("Email already exists: " + registerRequest.getEmail());
            throw new RuntimeException("Email is already in use!");
        }
        
        User user = new User(
            registerRequest.getName(),
            registerRequest.getEmail(),
            passwordEncoder.encode(registerRequest.getPassword())
        );
        user.setProvider(AuthProvider.LOCAL);
        
        System.out.println("Saving user to database: " + user.getEmail());
        User savedUser = userRepository.save(user);
        System.out.println("User saved successfully with ID: " + savedUser.getId());
        
        return "User registered successfully!";
    }
    
    public User getUserProfile(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> 
            new RuntimeException("User not found"));
    }
}