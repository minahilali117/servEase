package application;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuiltInServiceDBHandler {

    // Method to add a new service
    public boolean addService(String serviceName, String description, String category) {
        String query = "INSERT INTO BuiltInService (serviceName, description, category) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, serviceName);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, category);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding service: " + e.getMessage());
            return false;
        }
    }

    // Method to retrieve all services
    public List<BuiltInService> getAllServices() {
        String query = "SELECT * FROM BuiltInService";
        List<BuiltInService> services = new ArrayList<>();

        try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int serviceId = resultSet.getInt("serviceId");
                String serviceName = resultSet.getString("serviceName");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");

                services.add(new BuiltInService(serviceId, serviceName, description, category));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving services: " + e.getMessage());
        }
        return services;
    }

    // Method to update an existing service
    public boolean updateService(int serviceId, String serviceName, String description, String category) {
        String query = "UPDATE BuiltInService SET serviceName = ?, description = ?, category = ? WHERE serviceId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, serviceName);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, category);
            preparedStatement.setInt(4, serviceId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating service: " + e.getMessage());
            return false;
        }
    }

    // Method to delete a service
    public boolean deleteService(int serviceId) {
        String query = "DELETE FROM BuiltInService WHERE serviceId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, serviceId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting service: " + e.getMessage());
            return false;
        }
    }

    // Method to find a service by ID
    public BuiltInService getServiceById(int serviceId) {
        String query = "SELECT * FROM BuiltInService WHERE serviceId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, serviceId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String serviceName = resultSet.getString("serviceName");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");

                return new BuiltInService(serviceId, serviceName, description, category);
            }
        } catch (SQLException e) {
            System.err.println("Error finding service by ID: " + e.getMessage());
        }
        return null;
    }
}