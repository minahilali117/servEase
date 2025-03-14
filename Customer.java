package application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Customer extends User {
	private static int idCounter = 1; // Counter to generate unique IDs
    private int customerId;
    private int rating;
    private int numOfServicesTaken;
    private static CustomerDBHandler customerDBHandler;
    private static BookingDBHandler bookingDBHandler;
    private static List<Customer> customerDatabase = new ArrayList<>();
    public static List<Customer> getCustomerDatabase() {
    	//customerDatabase.clear();
    	customerDatabase=customerDBHandler.getAllCustomers();
    	return customerDatabase;
	}

	private static List<Booking> bookingDatabase = new ArrayList<>();
    
    public Customer() {
    	//super(null, null, null, null);
        this.customerId = idCounter++; // Assign unique ID
    }
 
    public Customer(String name, String email, String password, String phone) {
    	
        this(); // Call the default constructor to assign a unique ID
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        Customer.customerDBHandler = new CustomerDBHandler();  // Initialize the DB handler
        Customer.bookingDBHandler = new BookingDBHandler();
        //customerDatabase=customerDBHandler.getAllCustomers();
    }


    public static Customer getCustomerById(int customerId) {
        for (Customer customer : customerDatabase) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null; // Return null if customer not found
    }
    public boolean saveToDatabase() {
        return customerDBHandler.addCustomer(this);  // Calls the addCustomer method from CustomerDBHandler
    }
    
    public static Customer register(Scanner scanner) {
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
            return null;
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

        // Store customer details
        Customer newCustomer = new Customer(name, email, password, phone);
        customerDatabase.add(newCustomer);
        
     // Save the customer to the database using the saveToDatabase() method
        boolean isSaved = newCustomer.saveToDatabase(); 

        if (isSaved) {
            System.out.println("Customer registered successfully with email: " + email);
        } else {
            System.out.println("Failed to register customer. Please try again.");
            return null;
        }
        

        System.out.println("Customer registered successfully with email: " + email);
        return newCustomer;
    }

    // Validate email format
    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && email.indexOf("@") < email.lastIndexOf(".");
    }

    // Validate password length
    private static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    // Validate phone number format
    private static boolean isValidPhone(String phone) {
        return phone.matches("\\d{11}");
    }

    // Check email uniqueness
    private static boolean isEmailUnique(String email) {
        for (Customer customer : customerDatabase) {
            if (customer.email.equals(email)) {
                return false;
            }
        }
        return true;
    }
    

    public static Customer login(String email, String password) {
    	if (customerDBHandler == null) {
            // Make sure customerDBHandler is initialized before usage
            customerDBHandler = new CustomerDBHandler();  // Initialize it if not already initialized
        }
    	getCustomerDatabase();
        // Check credentials
        boolean isValid = false;
        for (Customer customer : customerDatabase) {
            if (customer.email.equals(email) && customer.password.equals(password)) {
            	System.out.println("Login successful! Welcome, " + customer.name);
                isValid = true;
                
                return customer; 
                //return true;
                
            }
        }
        	System.out.println("Invalid email or password. Please try again.");
            return null; // Return null if login fails

    }


    // Helper method to display services
    private void displayServices(List<Service> services) {
        for (Service service : services) {
            System.out.println(service);
        }
    }
    
    
    public void bookService(Scanner scanner) {
        List<Service> services = Service.getServices();

        if (services.isEmpty()) {
            System.out.println("No services available for booking at the moment.");
            return;
        }

        // Integrate browseServices functionality
        System.out.println("Available Services:");
        displayServicesWithProviders(services);

        // Provide sorting options
        System.out.println("\nWould you like to sort the services? (yes/no)");
        String response = scanner.next();

        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Sort by:");
            System.out.println("1. Price (Low to High)");
            System.out.println("2. Price (High to Low)");
            System.out.println("3. Category");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    services.sort(Comparator.comparingDouble(Service::getRatePerHour));
                    System.out.println("\nServices sorted by Price (Low to High):");
                    displayServicesWithProviders(services);
                    break;
                case 2:
                    services.sort(Comparator.comparingDouble(Service::getRatePerHour).reversed());
                    System.out.println("\nServices sorted by Price (High to Low):");
                    displayServicesWithProviders(services);
                    break;
                case 3:
                    services.sort(Comparator.comparing(Service::getCategory));
                    System.out.println("\nServices sorted by Category:");
                    displayServicesWithProviders(services);
                    break;
                default:
                    System.out.println("Invalid choice. Showing services without sorting.");
            }
        }

        // Provide filtering options
        System.out.println("\nDo you want to search for a service by name or category? (yes/no)");
        response = scanner.next();

        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Enter search term (name or category):");
            scanner.nextLine(); // Clear the input buffer
            String searchTerm = scanner.nextLine();
            boolean found = false;

            List<Service> filteredServices = new ArrayList<>();
            for (Service service : services) {
                if (service.getServiceName().equalsIgnoreCase(searchTerm) || service.getCategory().equalsIgnoreCase(searchTerm)) {
                    filteredServices.add(service);
                    found = true;
                }
            }

            if (found) {
                System.out.println("\nFiltered Services:");
                displayServicesWithProviders(filteredServices);
                services = filteredServices; // Update the list for further selection
            } else {
                System.out.println("No services match your search term.");
                return;
            }
        }

        // Select a service to book
        System.out.println("\nEnter the Service ID to book:");
        int serviceID = scanner.nextInt();
        Service selectedService = null;

        for (Service service : services) {
            if (service.getServiceId() == serviceID) {
                selectedService = service;
                break;
            }
        }

        if (selectedService == null) {
            System.out.println("Invalid Service ID. Please try again.");
            return;
        }

        // Enter booking details
        System.out.println("Enter Booking Date (YYYY-MM-DD):");
        scanner.nextLine(); // Clear the input buffer
        String date = scanner.nextLine();

        System.out.println("Enter Booking Time (HH:MM):");
        String time = scanner.nextLine();

        // Generate a booking ID (for simplicity, use current size of bookingDatabase + 1)
        int bookingID = Customer.getBookingDatabase().size() + 1;

     // Include providerId in the booking
        Booking newBooking = new Booking(
            bookingID,
            selectedService.getServiceId(),
            date,
            time,
            "Pending",
            this.getCustomerId(), // Current customer ID
            selectedService.getProviderId() // Provider ID from the selected service
        );

        Customer.bookingDBHandler.createBooking(newBooking);
        Customer.getBookingDatabase().add(newBooking);
        System.out.println("Booking created successfully. Awaiting provider confirmation.");
    }

    // Helper method to display services along with provider names
    private void displayServicesWithProviders(List<Service> services) {
        for (Service service : services) {
            String providerName = ServiceProvider.getProviderNameById(service.getProviderId());
            System.out.println("Service ID: " + service.getServiceId() +
                               ", Name: " + service.getServiceName() +
                               ", Description: " + service.getDescription() +
                               ", Price: $" + service.getRatePerHour() +
                               ", Category: " + service.getCategory() +
                               ", Provider: " + providerName);
        }
    }
    
    public void viewBookingHistory() {
        if (bookingDatabase.isEmpty()) {
            System.out.println("No booking history available.");
            return;
        }

        System.out.println("Your Booking History:");
        for (Booking booking : bookingDatabase) {
            Service bookedService = null;

            for (Service service : Service.getServices()) {
                if (service.getServiceId() == booking.getServiceID()) {
                    bookedService = service;
                    break;
                }
            }

            String serviceName = (bookedService != null) ? bookedService.getServiceName() : "Unknown Service";
            System.out.println("Booking ID: " + booking.getBookingID() +
                               ", Service: " + serviceName +
                               ", Date: " + booking.getDate() +
                               ", Time: " + booking.getTime() +
                               ", Status: " + booking.getStatus());
        }
    }
    
    
    public void provideFeedback(Scanner scanner) {
        if (bookingDatabase.isEmpty()) {
            System.out.println("No bookings found. Please book a service first.");
            return;
        }

        System.out.println("Your Completed Bookings:");
        List<Booking> completedBookings = new ArrayList<>();

        for (Booking booking : bookingDatabase) {
            if (booking.getStatus().equalsIgnoreCase("Confirmed")) {
                completedBookings.add(booking);
                Service bookedService = null;
                for (Service service : Service.getServices()) {
                    if (service.getServiceId() == booking.getServiceID()) {
                        bookedService = service;
                        break;
                    }
                }

                String serviceName = (bookedService != null) ? bookedService.getServiceName() : "Unknown Service";
                System.out.println("Booking ID: " + booking.getBookingID() +
                                   ", Service: " + serviceName +
                                   ", Date: " + booking.getDate() +
                                   ", Time: " + booking.getTime());
            }
        }

        if (completedBookings.isEmpty()) {
            System.out.println("No completed bookings available for feedback.");
            return;
        }

        System.out.println("Enter the Booking ID you want to provide feedback for:");
        int bookingID = scanner.nextInt();
        Booking selectedBooking = null;

        for (Booking booking : completedBookings) {
            if (booking.getBookingID() == bookingID) {
                selectedBooking = booking;
                break;
            }
        }

        if (selectedBooking == null) {
            System.out.println("Invalid Booking ID. Please try again.");
            return;
        }

        System.out.println("Enter your feedback:");
        scanner.nextLine(); // Clear input buffer
        String feedback = scanner.nextLine();

        System.out.println("Enter your rating (1 to 5):");
        int rating = scanner.nextInt();

        while (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Please enter a number between 1 and 5:");
            rating = scanner.nextInt();
        }

        // Store the feedback
        Feedback newFeedback = new Feedback(selectedBooking.getBookingID(), feedback, rating, customerId);
        Feedback.addFeedback(newFeedback);

        System.out.println("Thank you for your feedback! Here’s a summary:");
        System.out.println(newFeedback);
    }

    
    // Getters and Setters
    
    public static List<Booking> getBookingDatabase() {
    	//bookingDatabase=bookingDBHandler.getAllBookings();
		return bookingDatabase;
	}

	public static void setBookingDatabase(List<Booking> bookingDatabase) {
		Customer.bookingDatabase = bookingDatabase;
	}
	
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	 public int getRating() {
	        return rating;
	    }

	    public void setRating(int rating) {
	        this.rating = rating;
	    }

	    public int getNumOfServicesTaken() {
	        return numOfServicesTaken;
	    }

	    public void setNumOfServicesTaken(int numOfServicesTaken) {
	        this.numOfServicesTaken = numOfServicesTaken;
	    }
	
	@Override
    public void displayProfile() {
        System.out.println("Customer Profile:");
        System.out.println("ID: " + customerId + ", Name: " + name + ", Email: " + email + ", Phone: " + phone);
    } 
	@Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name + ", Email: " + email + ", Phone: " + phone;
    }
	public static void addCustomer(Customer customer) {
		customerDBHandler.addCustomer(customer);
	    	customerDatabase.add(customer);
	    	
	}
	   
	public static int getCustomerIdByEmail(String email) {
	    // Retrieve all customers from the database
	    getCustomerDatabase();

	    // Loop through the customer database to find the customer with the matching email
	    for (Customer customer : customerDatabase) {
	        if (customer.email.equals(email)) {
	            return customer.getCustomerId(); // Return the customerId when email matches
	        }
	    }

	    return -1;
	}

}