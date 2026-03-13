package StartupAi.StartupAi.dto;

import jakarta.validation.constraints.NotBlank;

public class IdeaGenerationRequest {
    @NotBlank(message = "Startup problem is required")
    private String startupProblem;
    
    private String userSkills;
    private String industry;
    private String targetAudience;
    private String budgetRange;
    private String preferredTechnology;
    private String innovationGoal;

    // Constructors
    public IdeaGenerationRequest() {}

    // Getters and Setters
    public String getStartupProblem() { return startupProblem; }
    public void setStartupProblem(String startupProblem) { this.startupProblem = startupProblem; }

    public String getUserSkills() { return userSkills; }
    public void setUserSkills(String userSkills) { this.userSkills = userSkills; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getTargetAudience() { return targetAudience; }
    public void setTargetAudience(String targetAudience) { this.targetAudience = targetAudience; }

    public String getBudgetRange() { return budgetRange; }
    public void setBudgetRange(String budgetRange) { this.budgetRange = budgetRange; }

    public String getPreferredTechnology() { return preferredTechnology; }
    public void setPreferredTechnology(String preferredTechnology) { this.preferredTechnology = preferredTechnology; }

    public String getInnovationGoal() { return innovationGoal; }
    public void setInnovationGoal(String innovationGoal) { this.innovationGoal = innovationGoal; }
}