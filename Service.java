package application;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private int serviceId;
    private String serviceName;
    private String description;
    private double ratePerHour;
    private String category;
    private int providerId;
    
    private static ServiceDBHandler serviceDBHandler = new ServiceDBHandler();
    private static List<Service> serviceDatabase = new ArrayList<>();
    
    public Service(int serviceID, String name, String description, double ratePerHour, String category, int providerId) {
        this.serviceId = serviceID;
        this.serviceName = name;
        this.description = description;
        this.ratePerHour = ratePerHour;
        this.category = category;
        this.providerId = providerId;
        Service.serviceDBHandler = new ServiceDBHandler();
    }
    
    public static void addService(Service service) {
        serviceDatabase.add(service);
      //Then save the service to the actual database
        if (serviceDBHandler.addService(service)) {
            System.out.println("Service added successfully to the database.");
        } else {
            System.out.println("Failed to add service to the database.");
        }
    }

    // Getter for the service database
    public static List<Service> getServices() {
    	serviceDatabase=serviceDBHandler.getAllServices();
        return serviceDatabase;
    }
    public boolean saveToDatabase() {
        return serviceDBHandler.addService(this);
    }
    
    @Override
    public String toString() {
        return "Service ID: " + serviceId + ", Name: " + serviceName + ", Description: " + description + 
               ", Price: $" + ratePerHour + ", Category: " + category;
    }

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
	
	public int getProviderId() {
        return providerId;
    }

	public double getRatePerHour() {
		return ratePerHour;
	}

	public void setRatePerHour(double price) {
		this.ratePerHour = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

   
    
}
