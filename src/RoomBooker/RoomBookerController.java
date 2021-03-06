package RoomBooker;

import Cleaner.CleanerModel;
import DBUtil.DBConnection;
import Login.LoginController;
import Login.LoginModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.sqlite.core.DB;

import javax.xml.soap.Text;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RoomBookerController implements Initializable {

    @FXML
    private ComboBox startTimeComboBox;

    @FXML
    private ComboBox endingTimeComboBox;

    @FXML
    private TextField refreshmentRequestTextField;

    @FXML
    private TextField refreshmentTimeTextField;

    @FXML
    private TextField resourcesTextField;

    @FXML
    private DatePicker meetingDateDatePicker;

    @FXML
    private Button bookRoomButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button cancelButton;



    @FXML
    private Spinner<Integer> roomNumberSpinner;

    private ArrayList<TimeSlot> bookedTimeSlots = new ArrayList<>();

    public void initialize() {
        setDateBounds();
        initializeSpinner();
        initComboBoxes();
    }



    public void setDateBounds() {
        LocalDate minDate = LocalDate.now();
        meetingDateDatePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(minDate));
                    }
                });
    }

    public void initializeSpinner() {
        roomNumberSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1));

    }

    public boolean verifyFieldsNotNull() {
        boolean startTimeEmpty = startTimeComboBox.getValue() == null;
        boolean endTimeEmpty = endingTimeComboBox.getValue() == null;
        boolean datePickerEmpty = meetingDateDatePicker.getValue() == null;
        if (startTimeEmpty || endTimeEmpty) {
            errorLabel.setText("Please select a time");
            return false;
        } else if (!refreshmentRequestTextField.getText().isEmpty() && refreshmentTimeTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter both refreshment fields");
            return false;
        } else if(datePickerEmpty) {
            errorLabel.setText("Please enter a date");
        }else{
            return true;
        }
        return true;
    }

    public void initComboBoxes() {
        ObservableList<String> startBoxTimeOptions = FXCollections.observableArrayList("08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30");
        ObservableList<String> endingBoxTimeOptions = FXCollections.observableArrayList("08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00");
        startTimeComboBox.getItems().addAll(startBoxTimeOptions);
        endingTimeComboBox.getItems().addAll(endingBoxTimeOptions);
    }

    public void storeMeetingDate() {

    }

    public boolean oneBookingPerDayPerUser() {
        return false;
    }

    //verify field null method (remember resources, refreshments and refreshments time relation)

    //method that will see if the user himself has two bookings in 2 or more different rooms at the same time

    //method that will make sure that the room is free for the selected time

    //method that will ensure that the refreshment times selected is valid/free and if it is valid or free you might want to also add to the refreshsments table if you've created one like me.

    //Create a new class that will hold end time and start time in regular slots or something similar
    //We need a method that will read from the database, get all the bookings, and then split it into necessary timeslots as needed
    //Then we need to do this math
    // Array(with all timeslots of a day) - Array(booked timeslots read from database) = available timeslot array
    //We should display the available time array and the user should be able to book only from this array.

    //We also need to make sure that the room can be cleaned before the next booking. Since there is only one cleaner, this means that we need to treat the cleaner as a user
    // and we cannot use him if he is already scheduled to clean another room. Therefore if the room cannot be cleaned before the next booking then the user should not be able to book for the given time
    //If the cleaner is free then also make a booking to the bookings table in the name of cleaner user. This username/userID combo can be used to get the cleaner's timetable later on for the cleanr's dashboard
    // My algorithm was this
    /*
    - check whether the cleaner can be booked for the immediate 30min after the booking
    - if not then check the 30 min right before the next time the room is booked
    - if both of those slots are busy then return false
     */

    public boolean checkRefreshmentTimeValidity() throws SQLException{
        if (refreshmentTimeTextField.getText().isEmpty()) {
            errorLabel.setText("");
            return true;
        }

        String[] refreshmentTimeArray = refreshmentTimeTextField.getText().split(",",0);

        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Refreshments WHERE RoomID = ? AND Date = ?";
        ArrayList<LocalTime> results = new ArrayList<>();

        try{
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setInt(1, roomNumberSpinner.getValue());
            ps.setString(2, meetingDateDatePicker.getValue().toString());

            rs = ps.executeQuery();
            while (rs.next()) {
                results.add(LocalTime.parse(rs.getString(3)));
            }

            for (String x : refreshmentTimeArray) {
                for (LocalTime existingRTS : results) {
                    LocalTime rts = LocalTime.parse(x);
                    if (rts.compareTo(existingRTS) == 0) {
                        return false;
                    }
                }
            }

            for (String x : refreshmentTimeArray) {
                String STToString = startTimeComboBox.getValue().toString();
                String ETToString = endingTimeComboBox.getValue().toString();
                LocalTime startingTime = LocalTime.parse(STToString);
                LocalTime endingTime = LocalTime.parse(ETToString);
                LocalTime rt = LocalTime.parse(x);

                boolean condition1 = rt.isAfter(startingTime);
                boolean condition2 = rt.isBefore(endingTime);

                if (condition1 && condition2) {
                    errorLabel.setText("");
                    return addRefreshment();
                }
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            assert ps != null;
            ps.close();
            assert rs != null;
            rs.close();
        }
    }

    public boolean addRefreshment() throws SQLException{
        String[] refreshmentArray = refreshmentRequestTextField.getText().split("[,]",0);
        String[] refreshmentTimeArray = refreshmentTimeTextField.getText().split("[,]",0);
        PreparedStatement ps = null;
        try{
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO Refreshments(RoomID, Date, Time, RefreshmentType) VALUES (?,?,?,?)";
            assert con != null;
            for (int i = 0; i < refreshmentArray.length; i++) {
                ps = con.prepareStatement(sql);
                ps.setInt(1, roomNumberSpinner.getValue());
                ps.setString(2, meetingDateDatePicker.getValue().toString());
                ps.setString(3, refreshmentTimeArray[i]);
                ps.setString(4,refreshmentArray[i]);
                ps.execute();
            }
            errorLabel.setText("");
            return true;
        } catch (Exception e){
            e.printStackTrace();
            errorLabel.setText("Error in refreshment field");
            return false;
        } finally{
            assert ps != null;
            ps.close();
        }
    }



    public boolean checkBookingStack() {

        String STToString = startTimeComboBox.getValue().toString();
        String ETToString = endingTimeComboBox.getValue().toString();
        LocalTime selectedStartingTime = LocalTime.parse(STToString);
        LocalTime selectedEndingTime = LocalTime.parse(ETToString);

        ArrayList<TimeSlot> currentBookingReq = TimeSlot.returnTimeSlots(selectedStartingTime, TimeSlot.getSlotNumber(selectedStartingTime, selectedEndingTime));
        for (TimeSlot bs : this.bookedTimeSlots){
            for (TimeSlot rs : currentBookingReq){
                if (rs.exists(bs)){
                    return false;
                }
            }
        }
        return true;
    }





    @FXML
    public void bookRoomConfirm() throws SQLException {
        if (verifyFieldsNotNull()){
            if(checkBookingStack()){
                if(checkForDoubleMeeting()){
                    if(checkRefreshmentTimeValidity()){
                        if(getRoomCleanStatus()) {
                            if (addBooking()) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Success!");
                                alert.setHeaderText(null);
                                alert.setContentText("Booking successfully added to DB!");
                                alert.showAndWait().ifPresent((btnType) -> {
                                    if (btnType == ButtonType.OK) {
                                        returnToCustomerUI();
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }
    @FXML
    public void returnToCustomerUI(){
        try{
            Stage old = (Stage)cancelButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/Customer/Customer.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Customer.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Customer Dashboard");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean getRoomCleanStatus() throws SQLException {
        String beginRoomCleaningTime= endingTimeComboBox.getValue().toString();
        CleanerModel cleaner = new CleanerModel(beginRoomCleaningTime, meetingDateDatePicker.getValue());
        cleaner.getOccupiedCleanerTimes();
        if(cleaner.isBookingOccupied()) {
            int roomID = roomNumberSpinner.getValue();
            String tempDate = meetingDateDatePicker.getValue().toString();
            if (cleaner.getNextBooking(tempDate) != null) {
                cleaner.addCleanerSlot(roomID, (cleaner.getNextBooking(tempDate)), meetingDateDatePicker.getValue());
                return true;
            } else {
                return false;
            }
        }else{
            return cleaner.addCleanerSlot(roomNumberSpinner.getValue(), LocalTime.parse(endingTimeComboBox.getValue().toString()),meetingDateDatePicker.getValue());
        }
    }

    private boolean checkForDoubleMeeting() throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        String StartDate = meetingDateDatePicker.getValue().toString();
        String sql = "SELECT * FROM Bookings WHERE UserID = ? and Date = ?";
        String STToString = startTimeComboBox.getValue().toString();
        String ETToString = endingTimeComboBox.getValue().toString();
        String date = meetingDateDatePicker.getValue().toString();
        LocalTime startingTime = LocalTime.parse(STToString);
        LocalTime endingTime = LocalTime.parse(ETToString);
        ArrayList<TimeSlot> timeResults = new ArrayList<>();
        try{
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setInt(1,LoginController.getUserID());
            ps.setString(2,date);

            ArrayList<TimeSlot> currentUserBooking = new ArrayList<>();
            ArrayList<TimeSlot> selectedTimeSlots = new ArrayList<>(TimeSlot.getSlotNumber(startingTime,endingTime));

            rs =ps.executeQuery();
            while (rs.next()){
                timeResults.add(new TimeSlot (rs.getString(4), rs.getString(5)));
            }

            for(TimeSlot x: timeResults){
                currentUserBooking.addAll(TimeSlot.returnTimeSlots(x.getStartingTime(),TimeSlot.getSlotNumber(x.getStartingTime(), x.getEndingTime())));
            }

            for(TimeSlot ub: currentUserBooking){
                for(TimeSlot rq: selectedTimeSlots){
                    if(rq.exists(ub)){
                        return false;
                    }
                }
            } return true;
        } catch (Exception e){
            e.printStackTrace();
        }finally{
            assert ps != null;
            assert rs != null;
            ps.close();
            rs.close();
        }
        return false;
    }

    public boolean addBooking() throws SQLException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO Bookings(RoomID, UserID, Username, StartingTime, EndingTime, Resources, Refreshment, RefreshmentTime) VALUES (?,?,?,?,?,?,?,?)";

        try {
            Connection con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, (roomNumberSpinner.getValue()));
            ps.setInt(2, (LoginController.UserID));
            ps.setString(3, LoginModel.getUsername(LoginController.UserID));
            ps.setString(4, startTimeComboBox.getValue().toString());
            ps.setString(5, endingTimeComboBox.getValue().toString());
            ps.setString(6, resourcesTextField.getText());
            ps.setString(7, refreshmentRequestTextField.getText());
            ps.setString(8, refreshmentTimeTextField.getText());
            errorLabel.setText("");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            assert ps !=null;
            ps.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
/*
things i need:
- verify that fields !null
- datepicker doesn't select a date before current day ???
- timeslot is empty
- user only has one booking per day
- assign cleaner to room for 30 mins after room is booked
- refreshment times availability, can only have one refreshment at one given time
- show user available booking times
 */
