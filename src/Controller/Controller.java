package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import hellofx.DatabaseUtil;

public class Controller {
    public static String username;
    public static String password;


    @FXML
    private Label labelInfo;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBack;

    @FXML
    private Button loginPage;

    @FXML
    private TextField usernameRegisterField;

    @FXML
    private TextField emailRegisterField;

    @FXML
    private PasswordField passwordRegisterField;

    @FXML
    private PasswordField passwordAgainField;

    @FXML
    private Button registerButton;

    @FXML
    private void signInButtonAction() {
        username = usernameField.getText();
        password = passwordField.getText();

        if(username.isEmpty() || password.isEmpty()) {
            System.out.println("Please enter a username and password");
            return;
        }

        if(DatabaseUtil.validateUser(username, password)) {
            System.out.println("Login successful");
            mainPageLoad();

        } else {
            System.out.println("Login failed");
            return;
        }

    }

    @FXML
    private void mainPageLoad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) signInButton.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void signUpButtonAction() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) signUpButton.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    private void reloadPageRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    @FXML
    private void loginPageAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/main.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) loginPage.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void registerAction() {
        String username = usernameRegisterField.getText();
        String email = emailRegisterField.getText();
        String password = passwordRegisterField.getText();
        String passwordAgain = passwordAgainField.getText();

        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordAgain.isEmpty()) {
            System.out.println("Please fill in all fields");
            labelInfo.setVisible(true);
            labelInfo.setText("Please fill in all fields! ");
            return;

        }

        if(!password.equals(passwordAgain)) {
            System.out.println("Passwords do not match");
            labelInfo.setVisible(true);
            labelInfo.setText("Passwords do not match! ");
            return;
        }

        if(DatabaseUtil.registerUser(username, password, email)) {
            System.out.println("Registration successful");
            reloadPageRegister();
        } else {
            System.out.println("Registration failed");
            labelInfo.setVisible(true);
            labelInfo.setText("Registration failed! ");
        }
    }



}
