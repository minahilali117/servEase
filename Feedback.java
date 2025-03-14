package application;

import java.util.ArrayList;
import java.util.List;

public class Feedback {
    private int bookingID;
    private String feedback;
    private int rating; // 1 to 5
    private int customerId;

    private static List<Feedback> feedbackDatabase = new ArrayList<>(); // Mock database for feedback

    public Feedback(int bookingID, String feedback, int rating, int customerId) {
        this.bookingID = bookingID;
        this.feedback = feedback;
        this.rating = rating;
        this.customerId = customerId;
    }

    public int getBookingID() {
        return bookingID;
    }

    public String getFeedback() {
        return feedback;
    }

    public int getRating() {
        return rating;
    }

    public static void addFeedback(Feedback feedback) {
        feedbackDatabase.add(feedback);
        FeedbackDBHandler feedbackDBHandler = new FeedbackDBHandler();
        feedbackDBHandler.addFeedback(feedback);
    }

    public static List<Feedback> getFeedbacks() {
        return feedbackDatabase;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingID + ", Feedback: " + feedback + ", Rating: " + rating + "/5";
    }

	public int getCustomerId() {
		
		return customerId;
	}
}