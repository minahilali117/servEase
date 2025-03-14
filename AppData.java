package application;
public class AppData {
    private static Customer loggedInCustomer;
    private static ServiceProvider loggedInSP;
    // Get the logged-in customer
    public static Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }
    public static ServiceProvider getLoggedInSP() {
        return loggedInSP;
    }
    public static void setLoggedInSP(ServiceProvider sp) {
    	loggedInSP = sp;
    }
    // Set the logged-in customer
    public static void setLoggedInCustomer(Customer customer) {
        loggedInCustomer = customer;
    }
}