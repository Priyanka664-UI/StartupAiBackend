package StartupAi.StartupAi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public class GoogleRegisterRequest {
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String googleId;

    public GoogleRegisterRequest() {}

    public GoogleRegisterRequest(String email, String name, String googleId) {
        this.email = email;
        this.name = name;
        this.googleId = googleId;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGoogleId() { return googleId; }
    public void setGoogleId(String googleId) { this.googleId = googleId; }
}