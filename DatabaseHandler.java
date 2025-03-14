package application;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler {
    private List<Object> database = new ArrayList<>();

    public boolean isEmailUnique(String email) {
        for (Object obj : database) {
            if (obj instanceof Customer && ((Customer) obj).getEmail().equals(email)) {
                return false;
            }
            if (obj instanceof ServiceProvider && ((ServiceProvider) obj).getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateCredentials(String email, String password) {
        for (Object obj : database) {
            if (obj instanceof Customer && ((Customer) obj).getEmail().equals(email)
                    && ((Customer) obj).getPassword().equals(password)) {
                return true;
            }
            if (obj instanceof ServiceProvider && ((ServiceProvider) obj).getEmail().equals(email)
                    && ((ServiceProvider) obj).getPassword().equals(password)) {
                return true;
            }
        }
        System.out.println("Invalid email or password.");
        return false;
    }

    public void store(Object obj) {
        database.add(obj);
    }

    public List<Service> retrieveServices(String criteria) {
        // Stub for service retrieval
        return new ArrayList<>();
    }
    

    // Delete a user
    public void deleteUser(String userType, int userId) {
        database.removeIf(obj -> (userType.equalsIgnoreCase("Customer") && obj instanceof Customer && ((Customer) obj).getCustomerId() == userId)
                || (userType.equalsIgnoreCase("ServiceProvider") && obj instanceof ServiceProvider && ((ServiceProvider) obj).getProviderId() == userId));
    }

    // View all users
    public void viewAllUsers(String userType) {
        for (Object obj : database) {
            if (userType.equalsIgnoreCase("Customer") && obj instanceof Customer) {
                System.out.println(obj);
            } else if (userType.equalsIgnoreCase("ServiceProvider") && obj instanceof ServiceProvider) {
                System.out.println(obj);
            }
        }
    }

    // View all services
    public void viewAllServices() {
        for (Object obj : database) {
            if (obj instanceof Service) {
                System.out.println(obj);
            }
        }
    }

    // Delete a service
    public void delete(String entityType, int entityId) {
        database.removeIf(obj -> entityType.equalsIgnoreCase("Service") && obj instanceof Service && ((Service) obj).getServiceId() == entityId);
    }
}
