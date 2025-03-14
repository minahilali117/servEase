package application;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServiceProvider extends User{
	private static int idCounter = 1; // Counter to generate unique IDs
    private int providerId;
    private int rating;
    private int numOfServicesCompleted;
    private List<Feedback> feedbackList; // List to store feedbacks
    
    private static ServiceProviderDBHandler dbHandler;
    private static FeedbackDBHandler feedbackDBhandler;
    private static List<ServiceProvider> providerDatabase = new ArrayList<>(); // In-memory service provider storage
    private static List<Booking> bookingDatabase = new ArrayList<>();


 // Getter and Setter for rating
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating >= 0 && rating <= 5) { // Assuming a rating scale of 0 to 5
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating must be between 0 and 5.");
        }
    }

    // Getter and Setter for numOfServicesCompleted
    public int getNumOfServicesCompleted() {
        return numOfServicesCompleted;
    }

    public void setNumOfServicesCompleted(int numOfServicesCompleted) {
        if (numOfServicesCompleted >= 0) { // Ensuring non-negative values
            this.numOfServicesCompleted = numOfServicesCompleted;
        } else {
            throw new IllegalArgumentException("Number of services completed cannot be negative.");
        }
    }

	@Override
    public void displayProfile() {
        System.out.println("Service Provider Profile:");
        System.out.println("ID: " + providerId + ", Name: " + name + ", Email: " + email + ", Phone: " + phone);
        System.out.println("Feedbacks:");
        for (Feedback feedback : feedbackList) {
            System.out.println(feedback);
        }
    }

    public ServiceProvider() {
    	//super(null,null,null,null);
        this.providerId = idCounter++; // Assign unique ID
        ServiceProvider.dbHandler = new ServiceProviderDBHandler(); // Initialize DB handler
    }

    public ServiceProvider(String name, String email, String password, String phone) {
        this(); // Call the default constructor to assign a unique ID
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.feedbackList = new ArrayList<>();
        ServiceProvider.feedbackDBhandler = new FeedbackDBHandler();
    }
    

    public ServiceProvider(int providerId, String name) {
        this.providerId = providerId;
        this.name = name;
    }
    
//  public ServiceProvider(String name, String email, String password, String phone) {
//  super(name, email, password, phone); // Call User constructor
//}


//	public ServiceProvider(int providerId, String name) {
////        super(name, null, null, null); // Call User constructor with partial data
////        this.userId = providerId; // Set userId from providerId
//    }
    public void addFeedback(Feedback feedback) {
    	boolean fback = feedbackDBhandler.addFeedback(feedback);
        this.feedbackList.add(feedback);
    }
    public void register(Scanner scanner) {
        System.out.println("Enter Name: ");
        String name = scanner.next();

        System.out.println("Enter Email: ");
        String email = scanner.next();

        while (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please enter a valid email: ");
            email = scanner.next();
        }

        if (!isEmailUnique(email)) {
            System.out.println("Email already exists. Please log in or use a different email.");
            return;
        }

        System.out.println("Enter Password: ");
        String password = scanner.next();

        while (!isValidPassword(password)) {
            System.out.println("Password must be at least 6 characters long. Please try again: ");
            password = scanner.next();
        }

        System.out.println("Enter Phone: ");
        String phone = scanner.next();

        while (!isValidPhone(phone)) {
            System.out.println("Invalid phone number. Please enter a valid 11-digit phone number: ");
            phone = scanner.next();
        }

        // Store service provider details
        ServiceProvider newProvider = new ServiceProvider(name, email, password, phone); // Ensure email is correctly passed
        addProvider(newProvider); // Add to the list of service providers

        System.out.println("Service provider registered successfully with email: " + email);
    }

    // Validate email format
    private boolean isValidEmail(String email) {
    	return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    // Validate password length
    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    // Validate phone number format
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{11}");
    }

    // Check email uniqueness
    private boolean isEmailUnique(String email) {
        for (ServiceProvider provider : providerDatabase) {
            // Check if email is not null and compare
            if (provider.getEmail() != null && provider.getEmail().equals(email)) {
                return false; // Return false if email already exists
            }
        }
        return true; // Return true if email is unique
    }
    
    public static ServiceProvider login(String email, String password) {
    	if (dbHandler == null) {
            // Make sure customerDBHandler is initialized before usage
    		dbHandler = new ServiceProviderDBHandler();  // Initialize it if not already initialized
        }
    	getProviderDatabase();
        // Check credentials
        boolean isValid = false;
        for (ServiceProvider sp : providerDatabase) {
            if (sp.email.equals(email) && sp.password.equals(password)) {
            	System.out.println("Login successful! Welcome, " + sp.name);
                isValid = true;
                
                return sp; 
                //return true;
                
            }
        }
        	System.out.println("Invalid email or password. Please try again.");
            return null; // Return null if login fails

    }
    
    
    public void viewRequests() {
        List<Booking> providerBookings = new ArrayList<>();

        // Filter the bookings by providerId
        for (Booking booking : Customer.getBookingDatabase()) {
            if (booking.getProviderId() == this.getProviderId() && booking.getStatus().equalsIgnoreCase("Pending")) {
                providerBookings.add(booking);
            }
        }

        if (providerBookings.isEmpty()) {
            System.out.println("No pending requests found.");
            return;
        }

        // Display pending requests
        System.out.println("Pending Requests for Provider ID: " + this.getProviderId());
        for (Booking booking : providerBookings) {
            System.out.println("Booking ID: " + booking.getBookingID() +
                               ", Service ID: " + booking.getServiceID() +
                               ", Date: " + booking.getDate() +
                               ", Time: " + booking.getTime() +
                               ", Customer ID: " + booking.getCustomerID());
        }
    }
    
    public void manageRequests(Scanner scanner) {
        List<Booking> providerBookings = new ArrayList<>();

        // Filter the bookings by providerId and Pending status
        for (Booking booking : Customer.getBookingDatabase()) {
            if (booking.getProviderId() == this.getProviderId() && booking.getStatus().equalsIgnoreCase("Pending")) {
                providerBookings.add(booking);
            }
        }

        if (providerBookings.isEmpty()) {
            System.out.println("No pending requests found.");
            return;
        }

        System.out.println("Pending Requests for Provider ID: " + this.getProviderId());
        for (Booking booking : providerBookings) {
            System.out.println("Booking ID: " + booking.getBookingID() +
                               ", Service ID: " + booking.getServiceID() +
                               ", Date: " + booking.getDate() +
                               ", Time: " + booking.getTime() +
                               ", Customer ID: " + booking.getCustomerID());
        }

        // Ask provider for action on the booking
        System.out.println("\nEnter Booking ID to manage:");
        int bookingID = scanner.nextInt();
        Booking selectedBooking = null;

        for (Booking booking : providerBookings) {
            if (booking.getBookingID() == bookingID) {
                selectedBooking = booking;
                break;
            }
        }

        if (selectedBooking == null) {
            System.out.println("Invalid Booking ID.");
            return;
        }

        // Choose whether to accept or reject the booking
        System.out.println("1. Accept Request");
        System.out.println("2. Reject Request");
        int action = scanner.nextInt();

        if (action == 1) {
            selectedBooking.setStatus("Confirmed");  // Mark as confirmed
            System.out.println("Booking ID " + selectedBooking.getBookingID() + " has been confirmed.");
        } else if (action == 2) {
            selectedBooking.setStatus("Rejected");  // Mark as rejected
            System.out.println("Booking ID " + selectedBooking.getBookingID() + " has been rejected.");
        } else {
            System.out.println("Invalid action. No changes made.");
        }
    }
    


    // View profile method to display provider details and feedbacks
    public void viewProfile() {
        // Displaying the basic profile information
        System.out.println("Service Provider Profile:");
        System.out.println("Name: " + this.getName());
        System.out.println("Email: " + this.getEmail());
        System.out.println("Phone: " + this.getPhone());

        // Display feedbacks related to this service provider
        List<Feedback> providerFeedbacks = Feedback.getFeedbacks(); // Get all feedbacks
        
        boolean foundFeedback = false;

        System.out.println("\nCustomer Feedbacks:");
        providerFeedbacks = feedbackDBhandler.getAllFeedbacks();
//        for (Feedback feedback : providerFeedbacks) {
//            // If feedback's providerId matches this provider's ID, show feedback
//            if (feedback.getBookingID() == this.providerId) { // Assuming feedback is tied to a booking's providerId
//                // Assuming you have a way to get the customer name by customerId, e.g., Customer.getCustomerById()
//                Customer customer = Customer.getCustomerById(feedback.getBookingID()); // Example to get customer info
//                System.out.println("Customer Name: " + customer.getName());
//                System.out.println("Feedback: " + feedback.getFeedback());
//                System.out.println("Rating: " + feedback.getRating() + "/5");
//                foundFeedback = true;
//            	
//            }
//        }

        if (!foundFeedback) {
            System.out.println("No feedback available for this provider yet.");
        }
    }
    

    // Static method to get provider by ID
    public static ServiceProvider getProviderById(int providerId) {
        // This method would return the provider object based on providerId
        // Assuming you have a static list of providers
        for (ServiceProvider provider : providerDatabase) {
            if (provider.getProviderId() == providerId) {
                return provider;
            }
           
        }
        return null;
    }

    
    public static void addProvider(ServiceProvider provider) {
        providerDatabase.add(provider);
        boolean flag = dbHandler.addServiceProvider(provider);
        
    }

    public static String getProviderNameById(int providerId) {
        for (ServiceProvider provider : providerDatabase) {
            if (provider.getProviderId() == providerId) {
                return provider.getName();
            }
        }
        return "Unknown Provider";
    }



 // Getters and Setters
    
	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
    public String toString() {
        return "Provider ID: " + providerId + ", Name: " + name + ", Email: " + email + ", Phone: " + phone;
    }

	public static List<ServiceProvider> getProviderDatabase() {
		providerDatabase=dbHandler.getAllServiceProviders();
		return providerDatabase;
	}

	public static void setProviderDatabase(List<ServiceProvider> providerDatabase) {
		ServiceProvider.providerDatabase = providerDatabase;
	}
    
}
