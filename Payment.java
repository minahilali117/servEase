package application;
import java.time.LocalDate;

public class Payment {
	private int paymentID;
    private int bookingID;
    private double amount;
    private String paymentDate;
    private String status; // "Success", "Failed"
    private String transactionID;

    public Payment(int paymentID, int bookingID, double amount, String status, String transactionID) {
        this.paymentID = paymentID;
        this.bookingID = bookingID;
        this.amount = amount;
        this.paymentDate = LocalDate.now().toString();
        this.status = status;
        this.transactionID = transactionID;
    }

 // Getters
    public int getPaymentID() {
        return paymentID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public String getTransactionID() {
        return transactionID;
    }

    @Override
    public String toString() {
        return "Payment ID: " + paymentID + ", Booking ID: " + bookingID + ", Amount: $" + amount + 
               ", Payment Date: " + paymentDate + ", Status: " + status + ", Transaction ID: " + transactionID;
    }
}

