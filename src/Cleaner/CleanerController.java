package Cleaner;

import Caterer.CatererModel;
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
import java.sql.*;
import java.util.ResourceBundle;

public class CleanerController{
    @FXML
    private Button backButton;
    @FXML
    private TableView<CleanerModel> CleanerTable;
    @FXML
    private TableColumn<CatererModel, String> roomIDColumn;
    @FXML
    private TableColumn<CatererModel, String> startTimeColumn;
    @FXML
    private TableColumn<CatererModel, String> endTimeColumn;
    @FXML
    private ObservableList<CleaningFreeSlot> slots;

    @FXML
    private void returnToAdmin() {
        try {
            Stage old = (Stage) this.backButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/Admin/AdminFXML.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Admin.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Admin");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void loadTable() throws SQLException {
        try {
            slots = FXCollections.observableArrayList();
            String sql = "SELECT * FROM Bookings WHERE UserID = ? AND StartingTime = ?";
            Connection con = DBConnection.getConnection();
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, 2);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                slots.add(new CleaningFreeSlot(rs.getString(1), rs.getString(4), rs.getString(5)));
            }

            for (CleaningFreeSlot i : slots) {
                System.out.println(i.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        this.startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        this.endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
    }

    public void initialize() {
        try {
            loadTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}