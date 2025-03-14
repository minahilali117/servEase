package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDBHandler {

    // Create a new admin
    public boolean createAdmin(Admin admin) {
        String query = "INSERT INTO Admins (name, email, password, phone) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, admin.getName());
            preparedStatement.setString(2, admin.getEmail());
            preparedStatement.setString(3, admin.getPassword());
            preparedStatement.setString(4, admin.getPhone());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating admin: " + e.getMessage());
            return false;
        }
    }

    // Retrieve an admin by ID
    public Admin getAdminById(int adminId) {
        String query = "SELECT * FROM Admins WHERE admin_id = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return extractAdminFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving admin by ID: " + e.getMessage());
        }
        return null;
    }

    // Retrieve an admin by email
    public Admin getAdminByEmail(String email) {
        String query = "SELECT * FROM Admins WHERE email = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return extractAdminFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving admin by email: " + e.getMessage());
        }
        return null;
    }


    // Extract Admin object from ResultSet
    private Admin extractAdminFromResultSet(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");

        Admin admin = new Admin(name, email, password);
        return admin;
    }
}