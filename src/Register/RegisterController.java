package Register;

import DBUtil.DBConnection;
import Login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML
    private Label errorLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private Button registerButton;

    @FXML
    private Button cancelButton;


    public boolean verifyEmail() {
        String regex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailField.getText());

        System.out.println(matcher.matches());
        if (!matcher.matches()) {
            errorLabel.setText("Invalid email address.");
        } else {
            errorLabel.setText("");
        }
        return matcher.matches();

    }

    public boolean isPasswordMatching() {
        if (passwordField.getText().equals(confirmPasswordField.getText())) {
            return true;
        } else {
            errorLabel.setText("Passwords not matching!");
            return false;
        }
    }

    public boolean addAccount() {
        String sql = "INSERT INTO Users (Username, Firstname, Lastname, Password, Email, Account) VALUES(?,?,?,?,?,?)";
        try {
            Connection con = DBConnection.getConnection();
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usernameField.getText());
            ps.setString(2, firstnameField.getText());
            ps.setString(3, lastnameField.getText());
            ps.setString(4, passwordField.getText());
            ps.setString(5, emailField.getText());
            ps.setString(6, "Customer");

            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public boolean fieldsNotNull() {

        if ((usernameField.getText() != null
                && firstnameField.getText() != null
                && lastnameField.getText() != null
                && emailField.getText() != null
                && passwordField.getText() != null)) {
            return true;

        } else {
            errorLabel.setText("bro no");
            return false;

        }
    }

    @FXML
    public void goBackToLogin() {
        try {
            Stage old = (Stage) this.registerButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/Login/LoginFXML.fxml").openStream());
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Login.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void registerUser() {
        if (fieldsNotNull()) {
            if (isPasswordMatching()) {
                if (verifyEmail()) {
                    if (addAccount()) {
                        goBackToLogin();
                    }
                }
            }
        }
    }
}
