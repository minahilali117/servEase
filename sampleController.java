package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class sampleController {
	@FXML
	private TextField tfTitle;
	@FXML
	private Button loginButton;
	@FXML
	private Button signupButton;
	@FXML
	private Button serviceProvider;
	@FXML
	private Button customer;
	@FXML
	private Button admin;
	@FXML
	private TextField email;
	@FXML
	private TextField password;
	@FXML
	private Button confirmSignupBtn;
	@FXML
	private Button confirmLoginBtn;
	@FXML
	private Button browseServicesBtn;
	@FXML
	private Button getHelpBtn;
	@FXML
	private Button viewBookingHistoryBtn;
	@FXML
	private Button viewProfileBtn;
	@FXML
	private Button bookServiceBtn;
	@FXML
	private Button viewDetailsBtn;
	@FXML
    private Button rebookServiceBtn;
	@FXML
	private TableView<String[]> tableView;


	@FXML
	private TextField searchbar;
	@FXML
	private Button goBack;

   @FXML
   private Button searchBtn;

	String userEmail;
	String userPass;
	int userID;

	@FXML
    private Button acceptRequestBtn;

    @FXML
    private TableView<?> bookingRequests;


    @FXML
    private Button viewClientProfileBtn;
    
    @FXML
    private Button offerServicesBtn;
    
    
    
    
    @FXML
    void acceptRequest(ActionEvent event) {

    }

    @FXML
    void viewClientProfile(ActionEvent event) {

    }
	 @FXML
	    void searchByKeyword(ActionEvent event) {

	    }
	@FXML
	void bookService(ActionEvent event) {

	}
	@FXML
    private Button addOfferBtn;

    @FXML
    private TableColumn<BuiltInService, String> category;

    @FXML
    private TableColumn<BuiltInService, String> description;

    @FXML
    private TableColumn<BuiltInService, String> service;

    @FXML
    private TableView<BuiltInService> builtInServices;
    
    private ObservableList<BuiltInService> serviceList = FXCollections.observableArrayList();
    

    
    
    

//    public void initialize() {
//        service.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
//        description.setCellValueFactory(new PropertyValueFactory<>("description"));
//        category.setCellValueFactory(new PropertyValueFactory<>("category"));
//        loadDataFromDatabase();
//
//    }
    private void loadDataFromDatabase() {
        String query = "SELECT * FROM BuiltInService";

        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int serviceId = resultSet.getInt("serviceId");
                String serviceName = resultSet.getString("serviceName");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");

                BuiltInService service = new BuiltInService(serviceId, serviceName, description, category);
                serviceList.add(service);
            }
        } catch (SQLException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }

        // Add data to TableView
        builtInServices.setItems(serviceList);
    }
	@FXML
    void offerServices()throws IOException
    {
		Stage stage = (Stage) offerServicesBtn.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("OfferServices.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login or Signup");
		primaryStage.setScene(scene);
		primaryStage.show();
    }
	@FXML
	public void loginOrSignup() throws IOException {
		Stage stage = (Stage) customer.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("loginOrSignup.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login or Signup");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	@FXML
	public void SPLoginOrSignup() throws IOException {
		Stage stage = (Stage) serviceProvider.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("SPLoginOrSignup.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login or Signup");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	public void login() throws IOException {
		Stage stage = (Stage) loginButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("LOGIN");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	@FXML
    private Button SPLoginButton;
	@FXML
	public void SPLogin() throws IOException {
		Stage stage = (Stage) SPLoginButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("SPLogin.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("LOGIN");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@FXML
	public void loginAdmin() throws IOException {
		Stage stage = (Stage) admin.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("LOGIN");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@FXML
	public void signup() throws IOException {
		Stage stage = (Stage) signupButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("SIGNUP");
		primaryStage.setScene(scene);
		primaryStage.show();

		// APPLY INPUT VALIDATION NO NUMBER WAGHAIRA

		// PROCEED TO LOGIN PAGE
		userEmail = email.getText();
		userPass = password.getText();


	}
    @FXML
    private TextField name;

    @FXML
    private TextField phone;
 // Helper method to validate email format
    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    // Helper method to validate phone number format
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{11}");
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to clear input fields
    private void clearFields() {
        name.clear();
        email.clear();
        password.clear();
        phone.clear();
    }

    @FXML
    private void confirmSignup() {//customer signup
        // Retrieve input values
        String customerName = name.getText().trim();
        String customerEmail = email.getText().trim();
        String customerPassword = password.getText().trim();
        String customerPhone = phone.getText().trim();

        // Validate inputs
        if (customerName.isEmpty() || customerEmail.isEmpty() || customerPassword.isEmpty() || customerPhone.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill out all fields.");
            return;
        }

        if (!isValidEmail(customerEmail)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid email format.");
            return;
        }

        if (customerPassword.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password must be at least 6 characters long.");
            return;
        }

        if (!isValidPhone(customerPhone)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid phone number format.");
            return;
        }

        // Create a new Customer object
        Customer newCustomer = new Customer(customerName, customerEmail, customerPassword, customerPhone);

        // Add customer to the database
        boolean isSuccess = newCustomer.saveToDatabase();

        if (isSuccess) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer registered successfully!");
            clearFields();

            // Navigate back to login screen
            navigateToLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to register customer. Try again.");
        }
    }

    private void navigateToLogin() {
        try {
            // Load the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent loginParent = loader.load();

            // Get the current stage
            Stage currentStage = (Stage) name.getScene().getWindow();

            // Set the new scene (login screen)
            Scene loginScene = new Scene(loginParent);
            currentStage.setScene(loginScene);

            // Optionally, you can set the stage title for the login screen
            currentStage.setTitle("Login");

            // Show the login scene
            currentStage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load login screen.");
            e.printStackTrace();
        }
    }

    @FXML
    private Button SPSignupButton;

    

    @FXML
    void SPSignup(ActionEvent event) throws IOException{
    	Stage stage = (Stage) SPSignupButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("SPSignup.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("SIGNUP");
		primaryStage.setScene(scene);
		primaryStage.show();

    }

    @FXML
    public void confirmLogin() throws IOException {
        userEmail = email.getText();
        userPass = password.getText();
        
        Customer loggedInCustomer = Customer.login(userEmail, userPass);

        if (loggedInCustomer != null) {
            // Store the logged-in customer in AppData
            AppData.setLoggedInCustomer(loggedInCustomer);

            Stage stage = (Stage) confirmLoginBtn.getScene().getWindow(); // Use correct button ID
            stage.close();
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("CustomerHomePage.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("HOME");
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid login details");
            alert.setHeaderText(null);
            alert.setContentText("Please check your email and password and try again.");
            alert.showAndWait();
        }
    }
    @FXML
    private Button SPConfirmLoginBtn;
    @FXML
    public void SPConfirmLogin() throws IOException {
        userEmail = email.getText();
        userPass = password.getText();
        System.out.println(userEmail);
        ServiceProvider loggedInSP = ServiceProvider.login(userEmail, userPass);

        if (loggedInSP != null) {
            // Store the logged-in customer in AppData
            AppData.setLoggedInSP(loggedInSP);
            
            Stage stage = (Stage) SPConfirmLoginBtn.getScene().getWindow(); // Use correct button ID
            stage.close();
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("SPHomePage.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("HOME");
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid login details");
            alert.setHeaderText(null);
            alert.setContentText("Please check your email and password and try again.");
            alert.showAndWait();
        }
    }



	@FXML
	void browseServices(ActionEvent event) throws IOException {
		Stage stage = (Stage) browseServicesBtn.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("Services.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("SERVICES");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void viewDetails() {
		// Get the selected row
		String[] selectedRow = tableView.getSelectionModel().getSelectedItem();

		if (selectedRow != null) {
			// Create a popup window to display the description
			Stage popupStage = new Stage();
			popupStage.setTitle("Service Description");

			// Create a TextArea to show the description
			TextArea descriptionArea = new TextArea(selectedRow[3]);
			descriptionArea.setWrapText(true);
			descriptionArea.setEditable(false);

			// Layout for the popup window
			VBox layout = new VBox(10);
			layout.getChildren().add(descriptionArea);
			layout.setPrefSize(400, 200);

			// Set the scene and show the popup
			Scene scene = new Scene(layout);
			popupStage.setScene(scene);
			popupStage.show();
		} else {
			// Show an alert if no row is selected
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("No Selection");
			alert.setHeaderText("No Service Selected");
			alert.setContentText("Please select a service to view its description.");
			alert.showAndWait();
		}
	}

	@FXML
	void getHelp(ActionEvent event) throws IOException {

	}

	@FXML
	void viewBookingHistory(ActionEvent event) throws IOException {
		Stage stage = (Stage) viewBookingHistoryBtn.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("BookingHistory.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("BOOKING HISTORY");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void viewProfile(ActionEvent event) throws IOException {
		Stage stage = (Stage) viewProfileBtn.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("ViewProfile.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("PROFIL");
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println("Hello, World!");

	}
	@FXML
    private Button goBacktoSPHP;
	@FXML
    void goBackToSPHomePage() throws IOException{
		Stage stage = (Stage) goBacktoSPHP.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("SPHomePage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("HOME");
		primaryStage.setScene(scene);
		primaryStage.show();
    }
	   @FXML
	    private Button SPviewProfileBtn;
	   @FXML
	    void SPviewProfile() throws IOException{
		   Stage stage = (Stage) SPviewProfileBtn.getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("SPViewProfile.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("PROFILE");
			primaryStage.setScene(scene);
			primaryStage.show();
	    }

	@FXML
	void rebookService(ActionEvent event) {

    }@FXML
    private TextField adminEmail;

    @FXML
    private TextField adminPassword;

    // Handle login action
    @FXML
    private void confirmAdminLogin() {
        String email = adminEmail.getText().trim();
        String password = adminPassword.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter both email and password.");
            return;
        }

        AdminDBHandler dbHandler = new AdminDBHandler();
        Admin admin = dbHandler.getAdminByEmail(email);

        if (admin != null) {
            // Check if password is not null and matches
            if (admin.getPassword() != null && admin.getPassword().equals(password)) {
                // Login successful, load Admin Dashboard
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
                    Stage stage = (Stage) adminEmail.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Admin not found.");
        }
    }


    @FXML
    private Button viewCustomersBtn;
    @FXML
    private Button viewServiceProvidersBtn;
    
    @FXML
    private void viewCustomers() {
        // You can replace this with actual functionality to show customer data
        showAlert(Alert.AlertType.INFORMATION, "View Customers", "Displaying all customers...");
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CustomerList.fxml"));
            Stage stage = (Stage) viewCustomersBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void viewServiceProviders() {
        // You can replace this with actual functionality to show service providers data
        showAlert(Alert.AlertType.INFORMATION, "View Service Providers", "Displaying all service providers...");

        // If you want to redirect to a new scene (Service provider list):
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ServiceProviderList.fxml"));
            Stage stage = (Stage) viewServiceProvidersBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}