package application;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDBHandler {

    // Method to add a service to the database
	public boolean addService(Service service) {
	    String query = "INSERT INTO Services (serviceName, description, price, category, providerId) VALUES (?, ?, ?, ?, ?)";
	    try (Connection connection = DatabaseUtils.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	        preparedStatement.setString(1, service.getServiceName());
	        preparedStatement.setString(2, service.getDescription());
	        preparedStatement.setDouble(3, service.getRatePerHour());  // Use setDouble for price (double)
	        preparedStatement.setString(4, service.getCategory());
	        preparedStatement.setInt(5, service.getProviderId());

	        return preparedStatement.executeUpdate() > 0;
	    } catch (SQLException e) {
	        System.err.println("Error adding service: " + e.getMessage());
	        return false;
	    }
	}

	// Method to retrieve a service by its ID
	public Service getServiceById(int serviceId) {
	    String query = "SELECT * FROM Services WHERE serviceId = ?";
	    try (Connection connection = DatabaseUtils.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	        preparedStatement.setInt(1, serviceId);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            return new Service(
	                    resultSet.getInt("serviceId"),
	                    resultSet.getString("serviceName"),
	                    resultSet.getString("description"),
	                    resultSet.getDouble("price"),  // Use getDouble to retrieve price as a double
	                    resultSet.getString("category"),
	                    resultSet.getInt("providerId")
	            );
	        }
	    } catch (SQLException e) {
	        System.err.println("Error retrieving service: " + e.getMessage());
	    }
	    return null;
	}


    // Method to retrieve all services
	public List<Service> getAllServices() {
	    String query = "SELECT * FROM Services";
	    List<Service> services = new ArrayList<>();
	    try (Connection connection = DatabaseUtils.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query);
	         ResultSet resultSet = preparedStatement.executeQuery()) {

	        while (resultSet.next()) {
	            Service service = new Service(
	                    resultSet.getInt("serviceId"),
	                    resultSet.getString("serviceName"),
	                    resultSet.getString("description"),
	                    resultSet.getDouble("price"),  // Use getDouble to retrieve price as a double
	                    resultSet.getString("category"),
	                    resultSet.getInt("providerId")
	            );
	            services.add(service);
	        }
	    } catch (SQLException e) {
	        System.err.println("Error retrieving all services: " + e.getMessage());
	    }
	    return services;
	}


    // Method to update a service
    public boolean updateService(Service service) {
        String query = "UPDATE Services SET serviceName = ?, description = ?, price = ?, category = ?, providerId = ? WHERE serviceId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, service.getServiceName());
            preparedStatement.setString(2, service.getDescription());
            preparedStatement.setDouble(3, service.getRatePerHour());
            preparedStatement.setString(4, service.getCategory());
            preparedStatement.setInt(5, service.getProviderId());
            preparedStatement.setInt(6, service.getServiceId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating service: " + e.getMessage());
            return false;
        }
    }

    // Method to delete a service
    public boolean deleteService(int serviceId) {
        String query = "DELETE FROM Services WHERE serviceId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, serviceId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting service: " + e.getMessage());
            return false;
        }
    }
}
