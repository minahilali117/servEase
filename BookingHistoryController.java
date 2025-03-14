package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingHistoryController {

    @FXML
    private TableView<Service> tableView;

    @FXML
    private TableColumn<Service, String> Name;

    @FXML
    private TableColumn<Service, Double> Rate;

    @FXML
    private TableColumn<Service, String> Category;

    @FXML
    private TableColumn<Service, Integer> ServiceProvider;

    private ObservableList<Service> services;


    @FXML
    public void initialize() {
        // Fetch the logged-in customer from AppData
        Customer loggedInCustomer = AppData.getLoggedInCustomer();

        if (loggedInCustomer != null) {
            // Set up columns to bind to the Service properties
            Name.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
            Rate.setCellValueFactory(new PropertyValueFactory<>("ratePerHour"));
            Category.setCellValueFactory(new PropertyValueFactory<>("category"));
            ServiceProvider.setCellValueFactory(new PropertyValueFactory<>("providerId"));

            // Fetch all services from the database
            ServiceDBHandler serviceDBHandler = new ServiceDBHandler();
            List<Service> serviceList = serviceDBHandler.getAllServices();

            // Simulating filtering services for the logged-in customer
            List<Service> filteredServices = new ArrayList<>();
            for (Service service : serviceList) {
                // Filtering by provider ID (simulating the customer's preference)
                if (service.getProviderId() == loggedInCustomer.getCustomerId() || service.getCategory().equalsIgnoreCase("Cleaning")) {
                    filteredServices.add(service);
                }
            }

            // Convert the filtered list to an ObservableList to update the UI
            services = FXCollections.observableArrayList(filteredServices);
            tableView.setItems(services); // Set the list to the table view
        } else {
            // Handle the case when there is no logged-in customer (perhaps show an error or redirect)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No customer is logged in.");
            alert.showAndWait();
        }
    }

    @FXML
    private Button rebookServiceBtn;
    @FXML
    private void rebookService() {
        // Get the selected service from the TableView
        Service selectedService = tableView.getSelectionModel().getSelectedItem();

        if (selectedService != null) {
            // Assume we have a logged-in customer (For demo, using a placeholder)
            Customer loggedInCustomer = new Customer("John Doe", "john@example.com", "password123", "123-456-7890");

            // Assuming a new booking ID can be generated (e.g., incremented)
            int newBookingID = generateNewBookingID();
            // Get current date and time dynamically
            LocalDate currentDate = LocalDate.now(); // Get today's date
            LocalTime currentTime = LocalTime.now(); // Get current time

            // Format the date and time to string for the booking
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            // Convert date and time to string format
            String formattedDate = currentDate.format(dateFormatter);
            String formattedTime = currentTime.format(timeFormatter);

            // Set the status to "Scheduled"
            String bookingStatus = "Scheduled";

            // Get the service ID and provider ID
            int serviceID = selectedService.getServiceId();
            int providerID = selectedService.getProviderId();

            // Create a new Booking with the provided information
            Booking newBooking = new Booking(newBookingID, serviceID, formattedDate, formattedTime, bookingStatus, loggedInCustomer.getCustomerId(), providerID);

            // Save the new booking to the database
            BookingDBHandler bookingDBHandler = new BookingDBHandler();
            if (bookingDBHandler.createBooking(newBooking)) {
                // Show a confirmation message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Rebooking Successful");
                alert.setHeaderText(null);
                alert.setContentText("Your service has been rebooked successfully.");
                alert.showAndWait();
            } else {
                // Show an error message if rebooking fails
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Rebooking Failed");
                alert.setHeaderText(null);
                alert.setContentText("There was an issue rebooking the service. Please try again later.");
                alert.showAndWait();
            }
        } else {
            // Show a message if no service was selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Service Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a service to rebook.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private Button goBackBtn;

    @FXML
    private void goBack() {
        // Close the current window
        Stage currentStage = (Stage) tableView.getScene().getWindow();
        currentStage.close();

        // Create a new stage and load the previous scene (e.g., a main menu or previous screen)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerHomePage.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, for example, show an error dialog
        }
    }

    // Helper method to generate a new booking ID (for the sake of this demo)
    private int generateNewBookingID() {
        // Logic for generating a new booking ID, e.g., increment the last ID or use a random number
        return (int) (Math.random() * 10000); // Placeholder random booking ID for demo purposes
    }

}