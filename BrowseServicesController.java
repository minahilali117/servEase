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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class BrowseServicesController {
    
    @FXML
    private TableView<Service> tableView1;
    @FXML
    private TableColumn<Service, String> Name1;
    @FXML
    private TableColumn<Service, String> Category1;
    @FXML
    private TableColumn<Service, String> ServiceProvider1;
    @FXML
    private TableColumn<Service, String> Description1;

    @FXML
    private TextField searchbar;

    private ObservableList<Service> serviceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        Name1.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        Category1.setCellValueFactory(new PropertyValueFactory<>("category"));
        Description1.setCellValueFactory(new PropertyValueFactory<>("description"));
        ServiceProvider1.setCellValueFactory(new PropertyValueFactory<>("providerId"));
        serviceList = FXCollections.observableArrayList(Service.getServices());
        tableView1.setItems(serviceList);
    }

    @FXML
    private Button viewDetailsBtn;
    @FXML
    public void viewDetails() {
        Service selectedService = tableView1.getSelectionModel().getSelectedItem();
        
        if (selectedService != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Service Details");
            alert.setHeaderText(selectedService.getServiceName());
            alert.setContentText("Description: " + selectedService.getDescription() +
                                 "\nCategory: " + selectedService.getCategory() +
                                 "\nProvider ID: " + selectedService.getProviderId() +
                                 "\nRate per Hour: " + selectedService.getRatePerHour());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Service Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a service to view details.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private Button bookServiceBtn;
 // Inside BrowseServicesController
    private static int bookingCounter = 1;  // Static counter to generate unique booking IDs

    private static int generateBookingId() {
        return bookingCounter++;  // Increment and return the current value
    }


    @FXML
    public void bookService() {
        Service selectedService = tableView1.getSelectionModel().getSelectedItem();
        
        if (selectedService != null) {
         //   int customerId = getCurrentCustomerId(); //get the id of the user thru their email and password
        	int customerId = 1;
            int providerId = selectedService.getProviderId(); 
            int serviceId = selectedService.getServiceId(); 
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            String status = "Pending"; 
            
            Booking newBooking = new Booking(generateBookingId(), serviceId, date, time, status, customerId, providerId);
            BookingDBHandler bookingDBHandler = new BookingDBHandler();
            boolean bookingCreated = bookingDBHandler.createBooking(newBooking);
            
            if (bookingCreated) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Booking Confirmed");
                alert.setHeaderText("Your booking has been confirmed.");
                alert.setContentText("Booking ID: " + newBooking.getBookingID() + "\nService: " + selectedService.getServiceName());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Booking Failed");
                alert.setHeaderText("Failed to book the service.");
                alert.setContentText("There was an error processing your booking. Please try again.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Service Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a service to book.");
            alert.showAndWait();
        }
    }

    @FXML
    private Button searchBtn;  // Declare the button for search

    @FXML
    public void search() {
        String keyword = searchbar.getText().trim();
        
        if (keyword.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Empty Search");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a keyword to search.");
            alert.showAndWait();
        } else {
            ObservableList<Service> filteredList = FXCollections.observableArrayList();
            
            for (Service service : serviceList) {
                if (service.getServiceName().toLowerCase().contains(keyword.toLowerCase()) || 
                    service.getCategory().toLowerCase().contains(keyword.toLowerCase()) ||
                    service.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredList.add(service);
                }
            }
            
            tableView1.setItems(filteredList);
            
            if (filteredList.isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("No Results Found");
                alert.setHeaderText(null);
                alert.setContentText("No services match your search criteria.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private Button goBack;

    @FXML
    public void goBack() {
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.close(); 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerHomePage.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}