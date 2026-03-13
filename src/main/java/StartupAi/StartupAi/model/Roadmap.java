package StartupAi.StartupAi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roadmaps")
public class Roadmap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idea_id")
    private Long ideaId;

    @Column(name = "step_number")
    private Integer stepNumber;

    @Column(name = "step_description", columnDefinition = "TEXT")
    private String stepDescription;

    @ManyToOne
    @JoinColumn(name = "idea_id", insertable = false, updatable = false)
    private StartupIdea startupIdea;

    // Constructors
    public Roadmap() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdeaId() { return ideaId; }
    public void setIdeaId(Long ideaId) { this.ideaId = ideaId; }

    public Integer getStepNumber() { return stepNumber; }
    public void setStepNumber(Integer stepNumber) { this.stepNumber = stepNumber; }

    public String getStepDescription() { return stepDescription; }
    public void setStepDescription(String stepDescription) { this.stepDescription = stepDescription; }

    public StartupIdea getStartupIdea() { return startupIdea; }
    public void setStartupIdea(StartupIdea startupIdea) { this.startupIdea = startupIdea; }
}