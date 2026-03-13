package StartupAi.StartupAi.dto;

public class BusinessModelDto {
    private String revenueStream;
    private String pricingStrategy;
    private String targetCustomers;

    // Getters and setters
    public String getRevenueStream() { return revenueStream; }
    public void setRevenueStream(String revenueStream) { this.revenueStream = revenueStream; }

    public String getPricingStrategy() { return pricingStrategy; }
    public void setPricingStrategy(String pricingStrategy) { this.pricingStrategy = pricingStrategy; }

    public String getTargetCustomers() { return targetCustomers; }
    public void setTargetCustomers(String targetCustomers) { this.targetCustomers = targetCustomers; }
}