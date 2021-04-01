package Customer;

import Admin.userBookings;
import DBUtil.DBConnection;
import Login.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;


public class CustomerController {

    private ObservableList<userBookings> data;
    @FXML
    private TableColumn<userBookings, Integer> roomIDColumn;
    @FXML
    private TableColumn<userBookings, String> startingTimeColumn;
    @FXML
    private TableColumn<userBookings, String> endingTimeColumn;
    @FXML
    private TableColumn<userBookings, String> dateColumn;

    @FXML
    private Button openRoomBookerButton;

    @FXML
    private void displayCurrentBookings(){
        try{
            PreparedStatement ps;
            Connection con = DBConnection.getConnection();
            ResultSet rs;
            String sql = "SELECT * FROM Bookings WHERE UserID IS ?";
            this.data= FXCollections.observableArrayList();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setString(1,String.valueOf(LoginController.currentUser.getUserID()));
            rs = ps.executeQuery();
            while (rs.next()){
                userBookings userBookingTemp = new userBookings(rs.getInt(1), rs.getInt(2), LocalTime.parse(rs.getString(3)), LocalTime.parse(rs.getString(4)), LocalDate.parse(rs.getString(5)));
                this.data.add(userBookingTemp);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        this.roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        this.startingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        this.endingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    @FXML
    public void openRoomBooker() {
        try {
            Stage old = (Stage) this.openRoomBookerButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/RoomBooker/RoomBooker.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/RoomBooker.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Room booker");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
