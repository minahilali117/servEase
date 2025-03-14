//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class ServEaseController {
//	
//	private List<Customer> customers = new ArrayList<>();
//    private List<ServiceProvider> serviceProviders = new ArrayList<>();
//    
//    public void registerCustomer(Scanner scanner) {
//        Customer newCustomer = new Customer();
//        newCustomer.register(scanner);
//        customers.add(newCustomer); // Add the new customer to the list
//    }
//    
//    public void registerServiceProvider(Scanner scanner) {
//        ServiceProvider newProvider = new ServiceProvider();
//        newProvider.register(scanner);
//        serviceProviders.add(newProvider); // Add the new provider to the list
//    }
//    
//    public Customer loginCustomer(Scanner scanner) {
//        for (Customer customer : customers) {
//            //if (customer.login(scanner)) {
//                return Customer.login(scanner); // Return the customer object if login is successful
//            //}
//        }
//        return null; // Return null if login fails
//    }
//    
//    public ServiceProvider loginServiceProvider(Scanner scanner) {
//        for (ServiceProvider provider : serviceProviders) {
//            if (provider.login(scanner)) {
//                return provider; // Return the provider object if login is successful
//            }
//        }
//        return null; // Return null if login fails
//    }
//
//    public void bookService(Scanner scanner, Customer customer) {
//        customer.bookService(scanner); // Use the customer object passed as a parameter
//    }
//
//    public void viewBookingHistory(Customer customer) {
//        customer.viewBookingHistory(); // Access the booking history of the specific customer
//    }
//
//    public void provideFeedback(Scanner scanner, Customer customer) {
//        customer.provideFeedback(scanner); // Provide feedback for the given customer
//    }
//
//    public void viewRequests(ServiceProvider provider) {
//        provider.viewRequests(); // View requests for the given provider
//    }
//
//    public void manageRequests(Scanner scanner, ServiceProvider provider) {
//        provider.manageRequests(scanner); // Manage requests for the given provider
//    }
//
////    public void viewProfile() {
////        providerHandler.viewProfile();
////    }
////
////    public void updateProfile(Scanner scanner) {
////        providerHandler.updateProfile(scanner);
////    }
//    
//    
//
//}
