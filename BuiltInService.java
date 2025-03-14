package application;
import java.util.ArrayList;
import java.util.List;

public class BuiltInService {
    private int serviceId;
    private String serviceName;
    private String description;
    private String category;

    // Static database handler instance
    private static BuiltInServiceDBHandler serviceDBHandler = new BuiltInServiceDBHandler();
    private static List<BuiltInService> serviceDatabase = new ArrayList<>();

    // Constructor
    public BuiltInService(int serviceId, String serviceName, String description, double ratePerHour, String category, int providerId) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.description = description;
        this.category = category;
    }

    // Constructor without ratePerHour and providerId (for flexibility)
    public BuiltInService(int serviceId, String serviceName, String description, String category) {
        this(serviceId, serviceName, description, 0.0, category, 0);
    }

    // Get all services from the database
    public static List<BuiltInService> getServices() {
        serviceDatabase = serviceDBHandler.getAllServices();
        return serviceDatabase;
    }

    // Save this service to the database
    public boolean saveToDatabase() {
        return serviceDBHandler.addService(this.serviceName, this.description, this.category);
    }

    // Update this service in the database
    public boolean updateInDatabase() {
        return serviceDBHandler.updateService(this.serviceId, this.serviceName, this.description, this.category);
    }

    // Delete this service from the database
    public boolean deleteFromDatabase() {
        return serviceDBHandler.deleteService(this.serviceId);
    }

//    @Override
//    public String toString() {
//        return "Service ID: " + serviceId +
//               ", Name: " + serviceName +
//               ", Description: " + description +
//               ", Rate: " + ratePerHour +
//               ", Category: " + category +
//               ", Provider ID: " + providerId;
//    }

    // Getters and Setters
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

   