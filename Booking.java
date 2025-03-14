package application;

import java.util.ArrayList;
import java.util.List;

public class Booking {
	private int bookingID;
    private int serviceID;
    private String date;
    private String time;
    private String status; // e.g., Pending, Confirmed, Rejected
    private int customerID;
    private int providerId;
    private static List<Booking> bookingDatabase = new ArrayList<>();

    public Booking(int bookingID, int serviceID, String date, String time, String status, int customerID, int providerId) {
        this.bookingID = bookingID;
        this.serviceID = serviceID;
        this.date = date;
        this.time = time;
        this.status = status;
        this.customerID = customerID;
        this.providerId = providerId; // Initialize providerId
    }
    


 // Getters and Setters
    public static void addBooking(Booking booking) {
    	bookingDatabase.add(booking);
        BookingDBHandler bookingDBHandler = new BookingDBHandler();
        bookingDBHandler.createBooking(booking);
    }
    
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    
    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }
    
    public int getBookingID() {
        return bookingID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingID + ", Service ID: " + serviceID + ", Date: " + date + 
               ", Time: " + time + ", Status: " + status;
    }
    
    
}