package Admin;

import DBUtil.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AdminController {

    @FXML
    private Button openCatererButton;
    @FXML
    private Button openCleanerButton;
    @FXML
    private Button addAdmin;
    @FXML
    private Button backButton;
    @FXML
    private TableView<userData> currentBookingTable;
    @FXML
    private TableView<userBookings> currentUserTable;
    @FXML
    private TableColumn<userData, String> IDColumn;
    @FXML
    private TableColumn<userData, String> usernameColumn;
    @FXML
    private TableColumn<userData, String> firstnameColumn;
    @FXML
    private TableColumn<userData, String> lastnameColumn;
    @FXML
    private TableColumn<userData, String> passwordColumn;
    @FXML
    private TableColumn<userData, String> emailColumn;
    @FXML
    private TableColumn<userData, String> accountTypeColumn;
    @FXML
    private TableColumn<userBookings, String> RoomIDColumn;
    @FXML
    private TableColumn<userBookings, String> UserIDColumn;
    @FXML
    private TableColumn<userBookings, String> UsernameColumn;
    @FXML
    private TableColumn<userBookings, String> startingTimeColumn;
    @FXML
    private TableColumn<userBookings, String> endingTimeColumn;
    @FXML
    private TableColumn<userBookings, String> dateColumn;
    @FXML
    private TableColumn<userBookings, String> resourcesColumn;
    @FXML
    private TableColumn<userBookings, String> refreshmentsColumn;
    @FXML
    private TableColumn<userBookings, String> refreshmentTimeColumn;
    private ObservableList<userData> data;
    private ObservableList<userBookings> bookings;


    private void initializeUserTable() {
        try {
            Connection con = DBConnection.getConnection();
            this.data = FXCollections.observableArrayList();
            String sql = "SELECT * FROM Users";
            assert con != null;
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                this.data.add(new userData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        this.firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("Firstname"));
        this.lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("Lastname"));
        this.passwordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        this.accountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("AccountType"));
        currentBookingTable.setItems(null);
        currentBookingTable.setItems(this.data);
    }

    private void initializeBookingTable() {
        try {
            Connection con = DBConnection.getConnection();
            this.bookings = FXCollections.observableArrayList();
            String sql = "SELECT * FROM Bookings";
            assert con != null;
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                this.bookings.add(new userBookings(rs.getInt(1),rs.getInt(2),rs.getString(3),LocalTime.parse(rs.getString(4)),LocalTime.parse(rs.getString(5)),LocalDate.parse(rs.getString(6)),rs.getString(7),rs.getString(8),rs.getString(9)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.RoomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        this.UserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        this.UsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        this.startingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startingTime"));
        this.endingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endingTime"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        this.resourcesColumn.setCellValueFactory(new PropertyValueFactory<>("resources"));
        this.refreshmentsColumn.setCellValueFactory(new PropertyValueFactory<>("refreshments"));
        this.refreshmentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("refreshmentsTime"));
        currentBookingTable.setItems(null);
        currentBookingTable.setItems(this.data);
    }

    @FXML
    private void openCaterer() {
        try {
            Stage old = (Stage) openCatererButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/Caterer/Caterer.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Caterer.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Caterer Dashboard");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openCleaner() {
        try {
            Stage old = (Stage) openCatererButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/Cleaner/Cleaner.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Cleaner.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Cleaner Dashboard");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void returnToLogin() {
        try {
            Stage old = (Stage) backButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/Login/LoginFXML.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Login.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addNewAdmin() {
        try {
            Stage old = (Stage) addAdmin.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/Admin/newAdmin.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Admin.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Cleaner Dashboard");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        initializeBookingTable();
        initializeUserTable();
    }
}
