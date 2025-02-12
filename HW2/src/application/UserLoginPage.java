package application;

import javafx.scene.Scene;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

/**
 * The UserLoginPage class provides a login interface for users to access their accounts.
 * It validates the user's credentials and navigates to the appropriate page upon successful login.
 */
public class UserLoginPage {
	
    private final DatabaseHelper databaseHelper;

    public UserLoginPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void show(Stage primaryStage) {
    	// Input field for the user's userName, password
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter userName");
        userNameField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(250);
        
        // Label to display error messages
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");


        Button loginButton = new Button("Login");
        
        loginButton.setOnAction(a -> {
        	// Retrieve user inputs
            String userName = userNameField.getText();
            String password = passwordField.getText();
            User user= new User(userName, password, "");
            try {
                if (databaseHelper.login(user)) {
                    // Check the number of roles assigned
                    List<String> roles = user.getRolesList();
                    if (roles.size() == 1) {
                        // Redirect to the corresponding home page based on the role
                        String role = roles.get(0);
                        switch (role) {
                            case "admin":
                                new AdminHomePage(databaseHelper).show(primaryStage);
                                break;
                            case "user":
                                new UserHomePage(databaseHelper).show(primaryStage);
                                break;
                            case "instructor":
                                new InstructorHomePage(databaseHelper).show(primaryStage);
                                break;
                            case "staff":
                                new StaffHomePage(databaseHelper).show(primaryStage);
                                break;
                            case "reviewer":
                                new ReviewerHomePage(databaseHelper).show(primaryStage);
                                break;
                           
                        }
                    } else {
                        // If multiple roles, show a selection dialog
                        showRoleDialog(primaryStage, roles);
                    }
                } else {
                    errorLabel.setText("Invalid username or password.");
                }
            } catch (SQLException ex) {
                errorLabel.setText("Database error: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(userNameField, passwordField, loginButton, errorLabel);

        primaryStage.setScene(new Scene(layout, 800, 400));
        primaryStage.setTitle("User Login");
        primaryStage.show();
     
    }
    
    private void showRoleDialog(Stage primaryStage, List<String> roles) {
    	// Create a dialog to let the user select their role
        ChoiceDialog<String> dialog = new ChoiceDialog<>(roles.get(0), roles);
        dialog.setTitle("Select Role");
        dialog.setHeaderText("You have multiple roles assigned. Please select one:");
        dialog.setContentText("Role:");
        
        dialog.showAndWait().ifPresent(selectedRole -> {
            switch (selectedRole) {
                case "admin":
                    new AdminHomePage(databaseHelper).show(primaryStage);
                    break;
                case "user":
                    new UserHomePage(databaseHelper).show(primaryStage);
                    break;
                case "instructor":
                    new InstructorHomePage(databaseHelper).show(primaryStage);
                    break;
                case "staff":
                    new StaffHomePage(databaseHelper).show(primaryStage);
                    break;
                case "reviewer":
                    new ReviewerHomePage(databaseHelper).show(primaryStage);
                    break;}});
        
        
    	
    	
    }
}