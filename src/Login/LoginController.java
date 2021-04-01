package Login;

import Customer.CustomerController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    LoginModel loginModel = new LoginModel();
    @FXML
    private Label connectionLabel;
    @FXML
    private Label credentialsLabel;
    @FXML
    private Label meetingBookerLabel;
    @FXML
    private Label accountCreationLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> accountType;
    public static int UserID;
    public static String username;
    public static User currentUser;


    public static int getUserID() {
        return UserID;
    }

    public static String getUsername(){
        return username;
    }

    public void initialize(URL url, ResourceBundle rb) {
        if (this.loginModel.isConnected()) {
            connectionLabel.setText("Connected to DB!");
        } else {
            connectionLabel.setText("DB offline");
        }
        accountType.getItems().addAll("Admin", "Customer");
    }

    @FXML
    public void login() {
        try {
            if (this.loginModel.isLogin(this.usernameField.getText(), this.passwordField.getText(), accountType.getValue())) {
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                switch (accountType.getValue()) {
                    case "Admin":
                        adminLogin();
                        break;
                    case "Customer":
                        customerLogin();
                        break;
                }
            } else {
                credentialsLabel.setText("Invalid log in!");
            }
        } catch (Exception ignored) {
            System.out.println("e");
        }
    }

//    @FXML
//    public void login() {
//        try {
//            int login = this.loginModel.isLogin(this.usernameField.getText(), this.passwordField.getText(), accountType.getValue());
//            if (login >0) {
//                UserID = login;
//                Stage stage = (Stage) this.loginButton.getScene().getWindow();
//                stage.close();
//                switch (accountType.getValue()) {
//                    case "Admin":
//                        adminLogin();
//                        break;
//                    case "Customer":
//                        customerLogin();
//                        break;
//                }
//            } else {
//                credentialsLabel.setText("Invalid log in!");
//            }
//        } catch (Exception ignored) {
//            System.out.println("Error");
//        }
//    }

    public void adminLogin() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/Admin/AdminFXML.fxml").openStream());
            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Admin.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Admin Dashboard");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void customerLogin() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/Customer/Customer.fxml").openStream());
            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Customer.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Customer Dashboard");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerUser() {
        try {
            Stage old = (Stage) registerButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/Register/Register.fxml").openStream());
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Register.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Register");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public void accountCreated(){
//        accountCreationLabel.setText("Account created!");
//    }
}

