package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProviderDBHandler {

    // Method to add a new service provider
    public boolean addServiceProvider(ServiceProvider serviceProvider) {
        String query = "INSERT INTO ServiceProvider (name, email, password, phone, gender, city, rating, numOfServicesCompleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, serviceProvider.getName());
            preparedStatement.setString(2, serviceProvider.getEmail());
            preparedStatement.setString(3, serviceProvider.getPassword());
            preparedStatement.setString(4, serviceProvider.getPhone());
            preparedStatement.setString(5, serviceProvider.getGender());
            preparedStatement.setString(6, serviceProvider.getCity());
            preparedStatement.setInt(7, serviceProvider.getRating());
            preparedStatement.setInt(8, serviceProvider.getNumOfServicesCompleted());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding service provider: " + e.getMessage());
            return false;
        }
    }

    // Method to retrieve a service provider by providerId
    public ServiceProvider getServiceProviderById(int providerId) {
        String query = "SELECT * FROM ServiceProvider WHERE providerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, providerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ServiceProvider serviceProvider = new ServiceProvider(
                        resultSet.getInt("providerId"),
                        resultSet.getString("name")
                );

                serviceProvider.setEmail(resultSet.getString("email"));
                serviceProvider.setPassword(resultSet.getString("password"));
                serviceProvider.setPhone(resultSet.getString("phone"));
                serviceProvider.setGender(resultSet.getString("gender"));
                serviceProvider.setCity(resultSet.getString("city"));
                serviceProvider.setRating(resultSet.getInt("rating"));
                serviceProvider.setNumOfServicesCompleted(resultSet.getInt("numOfServicesCompleted"));

                return serviceProvider;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving service provider: " + e.getMessage());
        }
        return null;
    }

    // Method to get all service providers
    public List<ServiceProvider> getAllServiceProviders() {
        String query = "SELECT * FROM ServiceProvider";
        List<ServiceProvider> serviceProviders = new ArrayList<>();
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ServiceProvider serviceProvider = new ServiceProvider(
                        resultSet.getInt("providerId"),
                        resultSet.getString("name")
                );

                serviceProvider.setEmail(resultSet.getString("email"));
                serviceProvider.setPassword(resultSet.getString("password"));
                serviceProvider.setPhone(resultSet.getString("phone"));
                serviceProvider.setGender(resultSet.getString("gender"));
                serviceProvider.setCity(resultSet.getString("city"));
                serviceProvider.setRating(resultSet.getInt("rating"));
                serviceProvider.setNumOfServicesCompleted(resultSet.getInt("numOfServicesCompleted"));

                serviceProviders.add(serviceProvider);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving service providers: " + e.getMessage());
        }
        return serviceProviders;
    }

    // Method to update a service provider's details
    public boolean updateServiceProvider(ServiceProvider serviceProvider) {
        String query = "UPDATE ServiceProvider SET name = ?, email = ?, password = ?, phone = ?, gender = ?, city = ?, rating = ?, numOfServicesCompleted = ? WHERE providerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, serviceProvider.getName());
            preparedStatement.setString(2, serviceProvider.getEmail());
            preparedStatement.setString(3, serviceProvider.getPassword());
            preparedStatement.setString(4, serviceProvider.getPhone());
            preparedStatement.setString(5, serviceProvider.getGender());
            preparedStatement.setString(6, serviceProvider.getCity());
            preparedStatement.setInt(7, serviceProvider.getRating());
            preparedStatement.setInt(8, serviceProvider.getNumOfServicesCompleted());
            preparedStatement.setInt(9, serviceProvider.getProviderId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating service provider: " + e.getMessage());
            return false;
        }
    }
 // Method to update the service provider's name
    public boolean updateServiceProviderName(int serviceProviderID, String newName) {
        String query = "UPDATE ServiceProvider SET name = ? WHERE providerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, serviceProviderID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating service provider name: " + e.getMessage());
            return false;
        }
    }

    // Method to update the service provider's password
    public boolean updateServiceProviderPassword(int serviceProviderID, String newPassword) {
        String query = "UPDATE ServiceProvider SET password = ? WHERE providerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, serviceProviderID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating service provider password: " + e.getMessage());
            return false;
        }
    }

    // Method to update the service provider's city
    public boolean updateServiceProviderCity(int serviceProviderID, String newCity) {
        String query = "UPDATE ServiceProvider SET city = ? WHERE providerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newCity);
            preparedStatement.setInt(2, serviceProviderID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating service provider city: " + e.getMessage());
            return false;
        }
    }

    // Method to update the service provider's gender
    public boolean updateServiceProviderGender(int serviceProviderID, String newGender) {
        String query = "UPDATE ServiceProvider SET gender = ? WHERE providerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newGender);
            preparedStatement.setInt(2, serviceProviderID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating service provider gender: " + e.getMessage());
            return false;
        }
    }

    // Method to delete a service provider by providerId
    public boolean deleteServiceProvider(int providerId) {
        String query = "DELETE FROM ServiceProvider WHERE providerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, providerId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting service provider: " + e.getMessage());
            return false;
        }
    }
}
