package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import databasePart1.DatabaseHelper;

import java.util.List;
import java.sql.SQLException;

/**
 * AdminPage class represents the user interface for the admin user.
 * This page displays a simple welcome message for the admin.
 */

public class AdminHomePage {
    private DatabaseHelper databaseHelper;

    public AdminHomePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
	/**
     * Displays the admin page in the provided primary stage.
     * @param primaryStage The primary stage where the scene will be displayed.
     */

    public void show(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Label to display the welcome message for the admin
        Label adminLabel = new Label("Hello, Admin!");
        adminLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox userList = new VBox(5);
        userList.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Button getUsers = new Button("Get Current Users");
        getUsers.setOnAction(a -> {
            try {
                List<User> users = databaseHelper.getAllUsers();
                userList.getChildren().clear();

//Looks through all the users in the database and lists them out in the admin homepage
                
                for (User user : users) {
                    Label userInformation = new Label(
                            "USERNAME: " + user.getUserName() +
                            ", ROLES: " + user.getRoles());
                    
//Created a button to where it will allow you to delete the users inside the admin homepage
                    Button deleteUserButton = new Button("Remove?");
                    deleteUserButton.setOnAction(e -> {
                        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmDialog.setHeaderText("Are you sure?");
                        confirmDialog.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                try {
                                    boolean deleted = databaseHelper.DeleteUser(user.getUserName(), "admin");
                                    if (deleted) {
                                        userList.getChildren().remove(userInformation);
                                        userList.getChildren().remove(deleteUserButton);
                                    }
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                    new Alert(Alert.AlertType.ERROR, "Failed to delete selected user: " + ex.getMessage()).show();
                                }
                            }
                        });
                    });

                    userList.getChildren().addAll(userInformation, deleteUserButton);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to retrieve users: " + e.getMessage()).show();
            }
        });

        layout.getChildren().addAll(adminLabel, getUsers, userList);
        Scene adminScene = new Scene(layout, 800, 400);

        // Set the scene to primary stage
        primaryStage.setScene(adminScene);
        primaryStage.setTitle("Admin Page");
    }
}