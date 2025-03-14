package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDBHandler {

    // Create a new feedback entry
    public boolean addFeedback(Feedback feedback) {
        String query = "INSERT INTO Feedbacks (bookingId, feedback, rating, customerId) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, feedback.getBookingID());
            preparedStatement.setString(2, feedback.getFeedback());
            preparedStatement.setInt(3, feedback.getRating());
            preparedStatement.setInt(4, feedback.getCustomerId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding feedback: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all feedback entries
    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM Feedbacks";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int bookingID = resultSet.getInt("bookingId");
                String feedbackText = resultSet.getString("feedback");
                int rating = resultSet.getInt("rating");
                int customerId = resultSet.getInt("customerId");

                Feedback feedback = new Feedback(bookingID, feedbackText, rating, customerId);
                feedbackList.add(feedback);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving feedbacks: " + e.getMessage());
        }
        return feedbackList;
    }

    // Update feedback by booking ID
    public boolean updateFeedback(int bookingId, String newFeedback, int newRating) {
        String query = "UPDATE Feedbacks SET feedback = ?, rating = ? WHERE bookingId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newFeedback);
            preparedStatement.setInt(2, newRating);
            preparedStatement.setInt(3, bookingId);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating feedback: " + e.getMessage());
            return false;
        }
    }

    // Delete feedback by booking ID
    public boolean deleteFeedback(int bookingId) {
        String query = "DELETE FROM Feedbacks WHERE bookingId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookingId);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting feedback: " + e.getMessage());
            return false;
        }
    }

    // Retrieve feedback by booking ID
    public Feedback getFeedbackByBookingId(int bookingId) {
        String query = "SELECT * FROM Feedbacks WHERE bookingId = ?";
        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookingId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String feedbackText = resultSet.getString("feedback");
                    int rating = resultSet.getInt("rating");
                    int customerId = resultSet.getInt("customerId");

                    return new Feedback(bookingId, feedbackText, rating, customerId);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving feedback: " + e.getMessage());
        }
        return null;
    }
}