package application;

//import java.util.List;
import java.util.Scanner;

public class Admin extends User{
    private String name;
    private String email;
    private String password;
    private String phone;

    public Admin(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
//    public Admin(String name, String email, String password, String phone) {
//        super(name, email, password, phone);
//    }

    @Override
    public void displayProfile() {
        System.out.println("Admin Profile:");
        System.out.println("ID: " +"id nahi hay iski "  + ", Name: " + name + ", Email: " + email + ", Phone: " + phone);
    }

    // Method to remove a customer by ID
    public void removeCustomer(Scanner scanner) {
        System.out.println("Enter Customer ID to remove:");
        int customerId = scanner.nextInt();

        // Create an instance of CustomerDBHandler
        CustomerDBHandler dbHandler = new CustomerDBHandler();

        // Attempt to delete from the database first
        boolean isDeletedFromDB = dbHandler.deleteCustomer(customerId);

        if (isDeletedFromDB) {
            // If successful, remove from local customer database
            Customer customerToRemove = null;
            for (Customer customer : Customer.getCustomerDatabase()) {
                if (customer.getCustomerId() == customerId) {
                    customerToRemove = customer;
                    break;
                }
            }

            if (customerToRemove != null) {
                Customer.getCustomerDatabase().remove(customerToRemove);
                System.out.println("Customer with ID " + customerId + " has been removed.");
            } else {
                System.out.println("Customer ID not found in the local database.");
            }
        } else {
            System.out.println("Failed to remove Customer with ID " + customerId + " from the database.");
        }
    }


    // Method to remove a service provider by ID
    public void removeServiceProvider(Scanner scanner) {
        System.out.println("Enter Service Provider ID to remove:");
        int providerId = scanner.nextInt();

        // Create an instance of ServiceProviderDBHandler
        ServiceProviderDBHandler dbHandler = new ServiceProviderDBHandler();

        // Attempt to delete from the database first
        boolean isDeletedFromDB = dbHandler.deleteServiceProvider(providerId);

        if (isDeletedFromDB) {
            // If successful, remove from local provider database
            ServiceProvider providerToRemove = null;
            for (ServiceProvider provider : ServiceProvider.getProviderDatabase()) {
                if (provider.getProviderId() == providerId) {
                    providerToRemove = provider;
                    break;
                }
            }

            if (providerToRemove != null) {
                ServiceProvider.getProviderDatabase().remove(providerToRemove);
                System.out.println("Service Provider with ID " + providerId + " has been removed.");
            } else {
                System.out.println("Service Provider ID not found in the local database.");
            }
        } else {
            System.out.println("Failed to remove Service Provider with ID " + providerId + " from the database.");
        }
    }



    // Method to view all customers and their details
    public void viewCustomers() {
        if (Customer.getCustomerDatabase().isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        System.out.println("Customer Details:");
        for (Customer customer : Customer.getCustomerDatabase()) {
            System.out.println(customer);
            System.out.println("Orders:");

            for (Booking booking : Customer.getBookingDatabase()) {
                if (booking.getCustomerID() == customer.getCustomerId()) {
                    System.out.println("  " + booking);
                }
            }

            System.out.println("Feedbacks:");
            for (Feedback feedback : Feedback.getFeedbacks()) {
                if (feedback.getCustomerId() == customer.getCustomerId()) {
                    System.out.println("  " + feedback);
                }
            }
            System.out.println("----------------------------------------");
        }
    }

    // Method to view all service providers and their details
    public void viewServiceProviders() {
        if (ServiceProvider.getProviderDatabase().isEmpty()) {
            System.out.println("No service providers found.");
            return;
        }

        System.out.println("Service Provider Details:");
        for (ServiceProvider provider : ServiceProvider.getProviderDatabase()) {
            System.out.println(provider);
            System.out.println("Services:");

            for (Service service : Service.getServices()) {
                if (service.getProviderId() == provider.getProviderId()) {
                    System.out.println("  " + service);
                }
            }

            System.out.println("Feedbacks:");
            for (Feedback feedback : Feedback.getFeedbacks()) {
                for (Booking booking : Customer.getBookingDatabase()) {
                    if (booking.getProviderId() == provider.getProviderId() && booking.getBookingID() == feedback.getBookingID()) {
                        System.out.println("  " + feedback);
                    }
                }
            }
            System.out.println("----------------------------------------");
        }
    }
    public String getPassword() {
        return password;
    }
    @Override
    public String toString() {
        return "Admin Name: " + name + ", Email: " + email;
    }
}
