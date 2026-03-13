package StartupAi.StartupAi.dto;

public class IdeaGenerationRequest {
    private String skills;
    private String interests;
    private String industry;
    private String budget;
    private String targetAudience;

    // Constructors
    public IdeaGenerationRequest() {}

    // Getters and Setters
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getBudget() { return budget; }
    public void setBudget(String budget) { this.budget = budget; }

    public String getTargetAudience() { return targetAudience; }
    public void setTargetAudience(String targetAudience) { this.targetAudience = targetAudience; }
}