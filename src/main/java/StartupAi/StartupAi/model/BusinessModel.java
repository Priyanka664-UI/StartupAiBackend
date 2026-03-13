package StartupAi.StartupAi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "business_models")
public class BusinessModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idea_id")
    private Long ideaId;

    @Column(name = "revenue_stream", columnDefinition = "TEXT")
    private String revenueStream;

    @Column(name = "pricing_strategy", columnDefinition = "TEXT")
    private String pricingStrategy;

    @Column(name = "target_customers", columnDefinition = "TEXT")
    private String targetCustomers;

    @ManyToOne
    @JoinColumn(name = "idea_id", insertable = false, updatable = false)
    private StartupIdea startupIdea;

    // Constructors
    public BusinessModel() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdeaId() { return ideaId; }
    public void setIdeaId(Long ideaId) { this.ideaId = ideaId; }

    public String getRevenueStream() { return revenueStream; }
    public void setRevenueStream(String revenueStream) { this.revenueStream = revenueStream; }

    public String getPricingStrategy() { return pricingStrategy; }
    public void setPricingStrategy(String pricingStrategy) { this.pricingStrategy = pricingStrategy; }

    public String getTargetCustomers() { return targetCustomers; }
    public void setTargetCustomers(String targetCustomers) { this.targetCustomers = targetCustomers; }

    public StartupIdea getStartupIdea() { return startupIdea; }
    public void setStartupIdea(StartupIdea startupIdea) { this.startupIdea = startupIdea; }
}