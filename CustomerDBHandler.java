package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDBHandler {

    // Method to insert a customer into the database
    public boolean addCustomer(Customer customer) {
        String query = "INSERT INTO Customer (name, email, password, phone, gender, city, rating, numOfServicesTaken) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPassword());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, customer.getGender());
            preparedStatement.setString(6, customer.getCity());
            preparedStatement.setInt(7, customer.getRating());
            preparedStatement.setInt(8, customer.getNumOfServicesTaken());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
            return false;
        }
    }

    // Method to retrieve a customer by ID
    public Customer getCustomerByID(int customerID) {
        String query = "SELECT * FROM Customer WHERE customerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Customer customer = new Customer(
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("phone")
                );
                // Set additional attributes
                customer.setCustomerId(resultSet.getInt("customerId"));
                customer.setGender(resultSet.getString("gender"));
                customer.setCity(resultSet.getString("city"));
                customer.setRating(resultSet.getInt("rating"));
                customer.setNumOfServicesTaken(resultSet.getInt("numOfServicesTaken"));
                return customer;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customer: " + e.getMessage());
        }
        return null;
    }

    // Method to retrieve all customers
    public List<Customer> getAllCustomers() {
        String query = "SELECT * FROM Customer";
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Customer customer = new Customer(
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("phone")
                );
                // Set additional attributes
                customer.setCustomerId(resultSet.getInt("customerId"));
                customer.setGender(resultSet.getString("gender"));
                customer.setCity(resultSet.getString("city"));
                customer.setRating(resultSet.getInt("rating"));
                customer.setNumOfServicesTaken(resultSet.getInt("numOfServicesTaken"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
        }
        return customers;
    }

    // Method to update a customer
    public boolean updateCustomer(Customer customer) {
        String query = "UPDATE Customer SET name = ?, email = ?, password = ?, phone = ?, gender = ?, city = ?, " +
                       "rating = ?, numOfServicesTaken = ? WHERE customerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPassword());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, customer.getGender());
            preparedStatement.setString(6, customer.getCity());
            preparedStatement.setInt(7, customer.getRating());
            preparedStatement.setInt(8, customer.getNumOfServicesTaken());
            preparedStatement.setInt(9, customer.getCustomerId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating customer: " + e.getMessage());
            return false;
        }
    }

    // Method to delete a customer
    public boolean deleteCustomer(int customerID) {
        String query = "DELETE FROM Customer WHERE customerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, customerID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        }
    }
    
 // Method to update the customer's name
    public boolean updateCustomerName(int customerID, String newName) {
        String query = "UPDATE Customer SET name = ? WHERE customerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, customerID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating customer name: " + e.getMessage());
            return false;
        }
    }

    // Method to update the customer's password
    public boolean updateCustomerPassword(int customerID, String newPassword) {
        String query = "UPDATE Customer SET password = ? WHERE customerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, customerID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating customer password: " + e.getMessage());
            return false;
        }
    }

    // Method to update the customer's city
    public boolean updateCustomerCity(int customerID, String newCity) {
        String query = "UPDATE Customer SET city = ? WHERE customerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newCity);
            preparedStatement.setInt(2, customerID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating customer city: " + e.getMessage());
            return false;
        }
    }
 // Method to update the customer's gender
    public boolean updateCustomerGender(int customerID, String newGender) {
        String query = "UPDATE Customer SET gender = ? WHERE customerId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newGender);
            preparedStatement.setInt(2, customerID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating customer gender: " + e.getMessage());
            return false;
        }
    }


}