package StartupAi.StartupAi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "competitors")
public class Competitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idea_id")
    private Long ideaId;

    @Column(name = "competitor_name")
    private String competitorName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String website;

    @ManyToOne
    @JoinColumn(name = "idea_id", insertable = false, updatable = false)
    private StartupIdea startupIdea;

    // Constructors
    public Competitor() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdeaId() { return ideaId; }
    public void setIdeaId(Long ideaId) { this.ideaId = ideaId; }

    public String getCompetitorName() { return competitorName; }
    public void setCompetitorName(String competitorName) { this.competitorName = competitorName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public StartupIdea getStartupIdea() { return startupIdea; }
    public void setStartupIdea(StartupIdea startupIdea) { this.startupIdea = startupIdea; }
}