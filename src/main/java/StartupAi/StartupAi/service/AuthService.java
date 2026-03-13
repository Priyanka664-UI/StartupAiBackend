package StartupAi.StartupAi.service;

import StartupAi.StartupAi.dto.LoginRequest;
import StartupAi.StartupAi.dto.RegisterRequest;
import StartupAi.StartupAi.dto.JwtResponse;
import StartupAi.StartupAi.model.User;
import StartupAi.StartupAi.repository.UserRepository;
import StartupAi.StartupAi.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        
        String jwt = jwtUtils.generateJwtToken(authentication);
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        
        return new JwtResponse(jwt, user.getId(), user.getName(), user.getEmail());
    }
    
    public String register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }
        
        User user = new User(
            registerRequest.getName(),
            registerRequest.getEmail(),
            passwordEncoder.encode(registerRequest.getPassword())
        );
        
        userRepository.save(user);
        return "User registered successfully!";
    }
    
    public User getUserProfile(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> 
            new RuntimeException("User not found"));
    }
}