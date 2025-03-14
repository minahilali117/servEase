package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDBHandler {

    // Create a new booking
    public boolean createBooking(Booking booking) {
        String query = "INSERT INTO Bookings (serviceId, bookingDate, bookingTime, status, customerId, providerId) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, booking.getServiceID());
            preparedStatement.setString(2, booking.getDate());
            preparedStatement.setString(3, booking.getTime());
            preparedStatement.setString(4, booking.getStatus());
            preparedStatement.setInt(5, booking.getCustomerID());
            preparedStatement.setInt(6, booking.getProviderId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating booking: " + e.getMessage());
            return false;
        }
    }

    // Retrieve a booking by ID
    public Booking getBookingById(int bookingId) {
        String query = "SELECT * FROM Bookings WHERE bookingId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return extractBookingFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving booking by ID: " + e.getMessage());
        }
        return null;
    }

    // Retrieve all bookings
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Bookings";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookings.add(extractBookingFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all bookings: " + e.getMessage());
        }
        return bookings;
    }

    // Update a booking's status
    public boolean updateBookingStatus(int bookingId, String newStatus) {
        String query = "UPDATE Bookings SET status = ? WHERE bookingId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, bookingId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating booking status: " + e.getMessage());
            return false;
        }
    }

    // Delete a booking by ID
    public boolean deleteBooking(int bookingId) {
        String query = "DELETE FROM Bookings WHERE bookingId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookingId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting booking: " + e.getMessage());
            return false;
        }
    }

    // Extract Booking object from ResultSet
    private Booking extractBookingFromResultSet(ResultSet resultSet) throws SQLException {
        int bookingID = resultSet.getInt("bookingId");
        int serviceID = resultSet.getInt("serviceId");
        String date = resultSet.getString("bookingDate");
        String time = resultSet.getString("bookingTime");
        String status = resultSet.getString("status");
        int customerID = resultSet.getInt("customerId");
        int providerID = resultSet.getInt("providerId");

        return new Booking(bookingID, serviceID, date, time, status, customerID, providerID);
    }
}
